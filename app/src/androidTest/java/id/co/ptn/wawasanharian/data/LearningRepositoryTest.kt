package id.co.ptn.wawasanharian.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.co.ptn.wawasanharian.data.local.WawasanDatabase
import id.co.ptn.wawasanharian.data.repository.OfflineLearningRepository
import id.co.ptn.wawasanharian.data.seed.ContentSeeder
import id.co.ptn.wawasanharian.data.seed.SeedCurriculum
import id.co.ptn.wawasanharian.domain.LearningRules
import id.co.ptn.wawasanharian.domain.LessonStatus
import id.co.ptn.wawasanharian.domain.SectionKind
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Exercises the repository against a real on-device SQLite file (not in-memory). */
@RunWith(AndroidJUnit4::class)
class LearningRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val dbName = "learning-repository-test.db"
    private var time = 1_000L
    private lateinit var db: WawasanDatabase
    private lateinit var repository: OfflineLearningRepository

    @Before
    fun setUp() {
        context.deleteDatabase(dbName)
        open()
    }

    @After
    fun tearDown() {
        db.close()
        context.deleteDatabase(dbName)
    }

    private fun open(contentVersion: Int = SeedCurriculum.VERSION) {
        db = WawasanDatabase.build(context, dbName)
        repository = OfflineLearningRepository(
            contentDao = db.contentDao(),
            progressDao = db.progressDao(),
            seeder = ContentSeeder(db.contentDao(), version = contentVersion),
            clock = { time++ },
        )
    }

    private fun reopen(contentVersion: Int = SeedCurriculum.VERSION) {
        db.close()
        open(contentVersion)
    }

    private fun statusOf(lessonId: String) = runBlocking {
        repository.observeCurriculum().first().flatMap { it.lessons }.first { it.id == lessonId }.status
    }

    @Test
    fun seedsFullCurriculumInOrder() = runBlocking {
        val stages = repository.observeCurriculum().first()
        assertEquals(6, stages.size)
        val lessons = stages.flatMap { it.lessons }
        assertEquals(12, lessons.size)
        assertEquals((1..12).toList(), lessons.map { it.number })
        assertEquals("s1-l1", lessons.first().id)
        assertEquals("s6-l2", lessons.last().id)
        assertTrue(lessons.all { it.status == LessonStatus.NOT_STARTED })
    }

    @Test
    fun newUser_defaultLessonIsFirst() = runBlocking {
        assertEquals("s1-l1", LearningRules.currentLesson(repository.observeCurriculum().first())?.id)
    }

    @Test
    fun lessonDetail_hasContentAndNeighbours() = runBlocking {
        val detail = repository.observeLessonDetail("s1-l2").first()
        assertNotNull(detail!!)
        assertEquals("s1", detail.stage.id)
        assertEquals(SectionKind.KENALI, detail.sections.first().kind)
        assertTrue(detail.keyTerms.isNotEmpty())
        assertTrue(detail.everydayExamples.size >= 2)
        assertTrue(detail.conversationPrompts.size >= 2)
        assertEquals(4, detail.questionCount)
        assertEquals("s1-l1", detail.previousLessonId)
        assertEquals("s2-l1", detail.nextLessonId)
        assertEquals(12, detail.totalLessons)

        assertNull(repository.observeLessonDetail("s1-l1").first()!!.previousLessonId)
        assertNull(repository.observeLessonDetail("s6-l2").first()!!.nextLessonId)
        assertNull(repository.observeLessonDetail("does-not-exist").first())
    }

    @Test
    fun openingThenCompletingALesson() = runBlocking {
        repository.markLessonOpened("s1-l1")
        assertEquals(LessonStatus.IN_PROGRESS, statusOf("s1-l1"))

        repository.markLessonCompleted("s1-l1")
        assertEquals(LessonStatus.COMPLETED, statusOf("s1-l1"))

        // Re-opening a completed lesson keeps it completed.
        repository.markLessonOpened("s1-l1")
        assertEquals(LessonStatus.COMPLETED, statusOf("s1-l1"))

        val stages = repository.observeCurriculum().first()
        assertEquals("s1-l2", LearningRules.currentLesson(stages)?.id)
        assertEquals(1, LearningRules.progressSummary(stages).completedCount)
    }

    @Test
    fun lessonCanBeCompletedWithoutQuiz() = runBlocking {
        repository.markLessonCompleted("s3-l1")
        assertEquals(LessonStatus.COMPLETED, statusOf("s3-l1"))
    }

    @Test
    fun progressSurvivesDatabaseReopen() = runBlocking {
        repository.markLessonCompleted("s1-l1")
        repository.markLessonOpened("s1-l2")
        reopen()
        assertEquals(LessonStatus.COMPLETED, statusOf("s1-l1"))
        assertEquals(LessonStatus.IN_PROGRESS, statusOf("s1-l2"))
    }

    @Test
    fun contentUpgradeKeepsProgress() = runBlocking {
        repository.markLessonCompleted("s2-l1")
        repository.submitQuiz("s2-l1", emptyMap())
        reopen(contentVersion = SeedCurriculum.VERSION + 1)

        val lessons = repository.observeCurriculum().first().flatMap { it.lessons }
        assertEquals(12, lessons.size)
        assertEquals(LessonStatus.COMPLETED, lessons.first { it.id == "s2-l1" }.status)
        assertNotNull(repository.observeQuizStats("s2-l1").first().latest)
        assertEquals(SeedCurriculum.VERSION + 1, db.contentDao().minContentVersion())
    }

    @Test
    fun quizAttemptsAreScoredAndStored() = runBlocking {
        val questions = repository.getQuizQuestions("s1-l1")
        assertEquals(4, questions.size)
        assertTrue(questions.all { q -> q.options.count { it.isCorrect } == 1 })

        val allCorrect = questions.associate { q -> q.id to q.options.first { it.isCorrect }.id }
        val firstWrong = allCorrect + (questions[0].id to questions[0].options.first { !it.isCorrect }.id)

        assertEquals(3, repository.submitQuiz("s1-l1", firstWrong).score)
        assertEquals(4, repository.submitQuiz("s1-l1", allCorrect).score)
        assertEquals(2, repository.submitQuiz("s1-l1", emptyMap<String, String>() + firstWrong - questions[1].id).score)

        val stats = repository.observeQuizStats("s1-l1").first()
        assertEquals(2, stats.latest?.score)
        assertEquals(4, stats.best?.score)
        // Taking the quiz never changes completion.
        assertEquals(LessonStatus.NOT_STARTED, statusOf("s1-l1"))
    }

    @Test
    fun resetClearsProgressButKeepsContent() = runBlocking {
        repository.markLessonCompleted("s1-l1")
        repository.submitQuiz("s1-l1", emptyMap())
        repository.resetProgress()

        val lessons = repository.observeCurriculum().first().flatMap { it.lessons }
        assertEquals(12, lessons.size)
        assertTrue(lessons.all { it.status == LessonStatus.NOT_STARTED })
        assertNull(repository.observeQuizStats("s1-l1").first().latest)
    }
}
