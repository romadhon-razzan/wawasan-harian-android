package id.co.ptn.wawasanharian.testing

import id.co.ptn.wawasanharian.domain.ConversationPrompt
import id.co.ptn.wawasanharian.domain.KeyTerm
import id.co.ptn.wawasanharian.domain.LearningRepository
import id.co.ptn.wawasanharian.domain.LearningRules
import id.co.ptn.wawasanharian.domain.LearningStage
import id.co.ptn.wawasanharian.domain.LessonDetail
import id.co.ptn.wawasanharian.domain.LessonSection
import id.co.ptn.wawasanharian.domain.LessonStatus
import id.co.ptn.wawasanharian.domain.LessonSummary
import id.co.ptn.wawasanharian.domain.QuizAttempt
import id.co.ptn.wawasanharian.domain.QuizOption
import id.co.ptn.wawasanharian.domain.QuizQuestion
import id.co.ptn.wawasanharian.domain.QuizResult
import id.co.ptn.wawasanharian.domain.QuizStats
import id.co.ptn.wawasanharian.domain.SectionKind
import id.co.ptn.wawasanharian.domain.StageWithLessons
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/** In-memory repository: one stage with lessons "l1", "l2", each with two quiz questions. */
class FakeLearningRepository(private var now: Long = 1_000L) : LearningRepository {

    private val stage = LearningStage("s1", "Tahap", "Deskripsi", 1)
    private val lessonIds = listOf("l1", "l2")
    private val progress = MutableStateFlow<Map<String, Pair<LessonStatus, Long?>>>(emptyMap())
    private val attempts = MutableStateFlow<List<QuizAttempt>>(emptyList())

    val openedLessons = mutableListOf<String>()

    fun questionsFor(lessonId: String) = listOf(
        QuizQuestion("$lessonId-q1", "Soal 1", "Penjelasan 1", listOf(
            QuizOption("$lessonId-q1-a", "A", true), QuizOption("$lessonId-q1-b", "B", false),
        )),
        QuizQuestion("$lessonId-q2", "Soal 2", "Penjelasan 2", listOf(
            QuizOption("$lessonId-q2-a", "A", false), QuizOption("$lessonId-q2-b", "B", true),
        )),
    )

    private fun curriculum(p: Map<String, Pair<LessonStatus, Long?>>) = listOf(
        StageWithLessons(stage, lessonIds.mapIndexed { i, id ->
            val (status, at) = p[id] ?: (LessonStatus.NOT_STARTED to null)
            LessonSummary(id, "s1", "Judul $id", "Ringkasan", "Teaser", 15, i + 1, status, at,
                if (status == LessonStatus.COMPLETED) at else null)
        })
    )

    override fun observeCurriculum(): Flow<List<StageWithLessons>> = progress.map(::curriculum)

    override fun observeLessonDetail(lessonId: String): Flow<LessonDetail?> = progress.map { p ->
        val lessons = curriculum(p).first().lessons
        val index = lessons.indexOfFirst { it.id == lessonId }
        if (index < 0) return@map null
        LessonDetail(
            lesson = lessons[index],
            stage = stage,
            totalLessons = lessons.size,
            sections = listOf(
                LessonSection("a", SectionKind.KENALI, "Kenali", "Isi"),
                LessonSection("b", SectionKind.KETAHUI, "Ketahui", "Isi"),
                LessonSection("c", SectionKind.HUBUNGKAN, "Hubungkan", "Isi"),
                LessonSection("d", SectionKind.RANGKUM, "Rangkuman", "Isi"),
            ),
            keyTerms = listOf(KeyTerm("Istilah", "Arti")),
            everydayExamples = listOf("Contoh 1", "Contoh 2"),
            conversationPrompts = listOf(ConversationPrompt("Tanya", "Jawab")),
            questionCount = 2,
            previousLessonId = lessons.getOrNull(index - 1)?.id,
            nextLessonId = lessons.getOrNull(index + 1)?.id,
        )
    }

    override suspend fun getQuizQuestions(lessonId: String) = questionsFor(lessonId)

    override fun observeQuizStats(lessonId: String): Flow<QuizStats> =
        attempts.map { all -> LearningRules.quizStats(all.filter { it.lessonId == lessonId }) }

    override suspend fun markLessonOpened(lessonId: String) {
        openedLessons += lessonId
        progress.update { p ->
            val current = p[lessonId]?.first
            if (current == LessonStatus.COMPLETED) p else p + (lessonId to (LessonStatus.IN_PROGRESS to now++))
        }
    }

    override suspend fun markLessonCompleted(lessonId: String) {
        progress.update { it + (lessonId to (LessonStatus.COMPLETED to now++)) }
    }

    override suspend fun submitQuiz(lessonId: String, answers: Map<String, String>): QuizResult {
        val result = LearningRules.scoreQuiz(questionsFor(lessonId), answers)
        attempts.update { it + QuizAttempt(it.size + 1L, lessonId, result.score, result.total, now++) }
        return result
    }

    override suspend fun resetProgress() {
        progress.value = emptyMap()
        attempts.value = emptyList()
    }

    fun attemptCount() = attempts.value.size
}
