package id.co.ptn.wawasanharian.domain

import id.co.ptn.wawasanharian.domain.LessonStatus.COMPLETED
import id.co.ptn.wawasanharian.domain.LessonStatus.IN_PROGRESS
import id.co.ptn.wawasanharian.domain.LessonStatus.NOT_STARTED
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LearningRulesTest {

    // --- Fixtures -------------------------------------------------------------------------

    private fun lesson(
        id: String,
        stageId: String,
        number: Int,
        status: LessonStatus = NOT_STARTED,
        lastOpenedAt: Long? = null,
        completedAt: Long? = null,
    ) = LessonSummary(id, stageId, "Judul $id", "Ringkasan", "Teaser", 15, number, status, lastOpenedAt, completedAt)

    private fun curriculum(vararg statuses: Triple<LessonStatus, Long?, Long?>): List<StageWithLessons> {
        // Two stages, two lessons each: s1-l1, s1-l2, s2-l1, s2-l2.
        val ids = listOf("s1-l1", "s1-l2", "s2-l1", "s2-l2")
        val lessons = ids.mapIndexed { i, id ->
            val (status, opened, completed) = statuses.getOrElse(i) { Triple(NOT_STARTED, null, null) }
            lesson(id, id.substringBefore('-'), i + 1, status, opened, completed)
        }
        return listOf(
            StageWithLessons(LearningStage("s1", "Tahap 1", "", 1), lessons.take(2)),
            StageWithLessons(LearningStage("s2", "Tahap 2", "", 2), lessons.drop(2)),
        )
    }

    private val notStarted = Triple<LessonStatus, Long?, Long?>(NOT_STARTED, null, null)
    private fun inProgress(openedAt: Long) = Triple<LessonStatus, Long?, Long?>(IN_PROGRESS, openedAt, null)
    private fun completed(at: Long) = Triple<LessonStatus, Long?, Long?>(COMPLETED, at, at)

    // --- Current lesson ----------------------------------------------------------------------

    @Test
    fun newUser_currentLessonIsFirstLesson() {
        assertEquals("s1-l1", LearningRules.currentLesson(curriculum())?.id)
    }

    @Test
    fun afterCompletingFirst_currentLessonIsNext() {
        assertEquals("s1-l2", LearningRules.currentLesson(curriculum(completed(10)))?.id)
    }

    @Test
    fun inProgressLessonWinsOverFirstUnfinished() {
        val stages = curriculum(notStarted, notStarted, inProgress(50))
        assertEquals("s2-l1", LearningRules.currentLesson(stages)?.id)
    }

    @Test
    fun mostRecentlyOpenedInProgressLessonWins() {
        val stages = curriculum(inProgress(100), notStarted, inProgress(50))
        assertEquals("s1-l1", LearningRules.currentLesson(stages)?.id)
    }

    @Test
    fun allCompleted_noCurrentLesson() {
        val stages = curriculum(completed(1), completed(2), completed(3), completed(4))
        assertNull(LearningRules.currentLesson(stages))
    }

    // --- Progress summary --------------------------------------------------------------------

    @Test
    fun progressSummary_countsAndCurrentStage() {
        val summary = LearningRules.progressSummary(curriculum(completed(10), completed(20)))
        assertEquals(2, summary.completedCount)
        assertEquals(4, summary.totalCount)
        assertEquals(0.5f, summary.fraction, 0.0001f)
        assertEquals("s2", summary.currentStage?.id)
        assertFalse(summary.allCompleted)
    }

    @Test
    fun progressSummary_recentlyCompletedNewestFirstAndLimited() {
        val stages = curriculum(completed(10), completed(30), completed(20))
        val summary = LearningRules.progressSummary(stages, recentLimit = 2)
        assertEquals(listOf("s1-l2", "s2-l1"), summary.recentlyCompleted.map { it.id })
    }

    @Test
    fun progressSummary_allCompletedUsesLastStage() {
        val summary = LearningRules.progressSummary(curriculum(completed(1), completed(2), completed(3), completed(4)))
        assertTrue(summary.allCompleted)
        assertEquals("s2", summary.currentStage?.id)
    }

    @Test
    fun progressSummary_emptyCurriculum() {
        val summary = LearningRules.progressSummary(emptyList())
        assertEquals(0f, summary.fraction)
        assertNull(summary.currentStage)
        assertFalse(summary.allCompleted)
    }

    // --- Weekly count & stage progress -------------------------------------------------------

    @Test
    fun completedSince_countsOnlyRecentCompletions() {
        val stages = curriculum(completed(100), completed(500), inProgress(600))
        assertEquals(1, LearningRules.completedSince(stages, sinceMillis = 400))
        assertEquals(2, LearningRules.completedSince(stages, sinceMillis = 0))
    }

    @Test
    fun stageProgress_countsCompletedLessons() {
        val stages = curriculum(completed(1), inProgress(2))
        val first = LearningRules.stageProgress(stages[0])
        assertEquals(1, first.completed)
        assertEquals(2, first.total)
        assertEquals(0.5f, first.fraction, 0.0001f)
        assertEquals(0f, LearningRules.stageProgress(stages[1]).fraction, 0.0001f)
    }

    // --- Quiz scoring ------------------------------------------------------------------------

    private val questions = listOf(
        QuizQuestion("q1", "?", "", listOf(QuizOption("q1-a", "A", true), QuizOption("q1-b", "B", false))),
        QuizQuestion("q2", "?", "", listOf(QuizOption("q2-a", "A", false), QuizOption("q2-b", "B", true))),
        QuizQuestion("q3", "?", "", listOf(QuizOption("q3-a", "A", false), QuizOption("q3-b", "B", true))),
    )

    @Test
    fun scoreQuiz_allCorrect() {
        val result = LearningRules.scoreQuiz(questions, mapOf("q1" to "q1-a", "q2" to "q2-b", "q3" to "q3-b"))
        assertEquals(3, result.score)
        assertEquals(3, result.total)
    }

    @Test
    fun scoreQuiz_wrongAndUnansweredCountAsIncorrect() {
        val result = LearningRules.scoreQuiz(questions, mapOf("q1" to "q1-b", "q2" to "q2-b"))
        assertEquals(1, result.score)
        assertEquals(listOf(false, true, false), result.results.map { it.isCorrect })
        assertEquals("q1-a", result.results[0].correctOptionId)
        assertNull(result.results[2].selectedOptionId)
    }

    // --- Quiz stats --------------------------------------------------------------------------

    @Test
    fun quizStats_latestAndBest() {
        val attempts = listOf(
            QuizAttempt(1, "l", 2, 4, attemptedAt = 100),
            QuizAttempt(2, "l", 4, 4, attemptedAt = 200),
            QuizAttempt(3, "l", 3, 4, attemptedAt = 300),
        )
        val stats = LearningRules.quizStats(attempts)
        assertEquals(3L, stats.latest?.id)
        assertEquals(2L, stats.best?.id)
    }

    @Test
    fun quizStats_bestTieGoesToEarliest() {
        val attempts = listOf(QuizAttempt(2, "l", 3, 4, 200), QuizAttempt(1, "l", 3, 4, 100))
        assertEquals(1L, LearningRules.quizStats(attempts).best?.id)
    }

    @Test
    fun quizStats_noAttempts() {
        val stats = LearningRules.quizStats(emptyList())
        assertNull(stats.latest)
        assertNull(stats.best)
    }

}
