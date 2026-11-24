package id.co.ptn.wawasanharian.data.repository

import id.co.ptn.wawasanharian.data.local.ContentDao
import id.co.ptn.wawasanharian.data.local.LearningStageEntity
import id.co.ptn.wawasanharian.data.local.LessonEntity
import id.co.ptn.wawasanharian.data.local.ProgressDao
import id.co.ptn.wawasanharian.data.local.QuizAttemptEntity
import id.co.ptn.wawasanharian.data.local.UserLessonProgressEntity
import id.co.ptn.wawasanharian.data.seed.ContentSeeder
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/** Room-backed repository; everything works offline. */
class OfflineLearningRepository(
    private val contentDao: ContentDao,
    private val progressDao: ProgressDao,
    private val seeder: ContentSeeder,
    private val clock: () -> Long = System::currentTimeMillis,
) : LearningRepository {

    override fun observeCurriculum(): Flow<List<StageWithLessons>> = flow {
        seeder.ensureSeeded()
        emitAll(
            combine(
                contentDao.observeStages(),
                contentDao.observePublishedLessons(),
                progressDao.observeAll(),
            ) { stages, lessons, progress ->
                buildCurriculum(stages, lessons, progress.associateBy { it.lessonId })
            }
        )
    }.distinctUntilChanged()

    override fun observeLessonDetail(lessonId: String): Flow<LessonDetail?> = flow {
        seeder.ensureSeeded()
        val sections = contentDao.sections(lessonId).map {
            LessonSection(it.id, SectionKind.valueOf(it.kind), it.title, it.body)
        }
        val keyTerms = contentDao.keyTerms(lessonId).map { KeyTerm(it.term, it.definition) }
        val examples = contentDao.examples(lessonId).map { it.text }
        val prompts = contentDao.prompts(lessonId).map { ConversationPrompt(it.prompt, it.exampleResponse) }
        val questionCount = contentDao.questions(lessonId).size

        emitAll(observeCurriculum().map { stages ->
            val ordered = stages.flatMap { it.lessons }
            val index = ordered.indexOfFirst { it.id == lessonId }
            if (index < 0) return@map null
            val lesson = ordered[index]
            LessonDetail(
                lesson = lesson,
                stage = stages.first { it.stage.id == lesson.stageId }.stage,
                totalLessons = ordered.size,
                sections = sections,
                keyTerms = keyTerms,
                everydayExamples = examples,
                conversationPrompts = prompts,
                questionCount = questionCount,
                previousLessonId = ordered.getOrNull(index - 1)?.id,
                nextLessonId = ordered.getOrNull(index + 1)?.id,
            )
        })
    }.distinctUntilChanged()

    override suspend fun getQuizQuestions(lessonId: String): List<QuizQuestion> {
        seeder.ensureSeeded()
        val questions = contentDao.questions(lessonId)
        val optionsByQuestion = contentDao.options(questions.map { it.id }).groupBy { it.questionId }
        return questions.map { q ->
            QuizQuestion(
                id = q.id,
                question = q.question,
                explanation = q.explanation,
                options = optionsByQuestion[q.id].orEmpty().map { QuizOption(it.id, it.label, it.isCorrect) },
            )
        }
    }

    override fun observeQuizStats(lessonId: String): Flow<QuizStats> =
        progressDao.observeAttempts(lessonId).map { attempts ->
            LearningRules.quizStats(attempts.map { it.toDomain() })
        }

    override suspend fun markLessonOpened(lessonId: String) {
        val now = clock()
        val existing = progressDao.get(lessonId)
        val updated = when {
            existing == null -> UserLessonProgressEntity(
                lessonId = lessonId,
                status = LessonStatus.IN_PROGRESS.name,
                startedAt = now,
                completedAt = null,
                lastOpenedAt = now,
            )
            existing.status == LessonStatus.NOT_STARTED.name -> existing.copy(
                status = LessonStatus.IN_PROGRESS.name,
                startedAt = existing.startedAt ?: now,
                lastOpenedAt = now,
            )
            else -> existing.copy(lastOpenedAt = now)
        }
        progressDao.upsert(updated)
    }

    override suspend fun markLessonCompleted(lessonId: String) {
        val now = clock()
        val existing = progressDao.get(lessonId)
        progressDao.upsert(
            UserLessonProgressEntity(
                lessonId = lessonId,
                status = LessonStatus.COMPLETED.name,
                startedAt = existing?.startedAt ?: now,
                // Keep the first completion time so "recently completed" stays honest.
                completedAt = existing?.completedAt ?: now,
                lastOpenedAt = now,
            )
        )
    }

    override suspend fun submitQuiz(lessonId: String, answers: Map<String, String>): QuizResult {
        val result = LearningRules.scoreQuiz(getQuizQuestions(lessonId), answers)
        progressDao.insertAttempt(
            QuizAttemptEntity(
                lessonId = lessonId,
                score = result.score,
                totalQuestions = result.total,
                attemptedAt = clock(),
            )
        )
        return result
    }

    override suspend fun resetProgress() {
        progressDao.clearAll()
    }

    private fun buildCurriculum(
        stages: List<LearningStageEntity>,
        lessons: List<LessonEntity>,
        progress: Map<String, UserLessonProgressEntity>,
    ): List<StageWithLessons> {
        val lessonsByStage = lessons.groupBy { it.stageId }
        var number = 0
        return stages.sortedBy { it.sortOrder }.map { stage ->
            StageWithLessons(
                stage = LearningStage(stage.id, stage.title, stage.description, stage.sortOrder),
                lessons = lessonsByStage[stage.id].orEmpty().sortedBy { it.sortOrder }.map { lesson ->
                    number += 1
                    val p = progress[lesson.id]
                    LessonSummary(
                        id = lesson.id,
                        stageId = lesson.stageId,
                        title = lesson.title,
                        summary = lesson.summary,
                        widgetTeaser = lesson.widgetTeaser,
                        estimatedMinutes = lesson.estimatedMinutes,
                        number = number,
                        status = p?.status?.let(LessonStatus::valueOf) ?: LessonStatus.NOT_STARTED,
                        lastOpenedAt = p?.lastOpenedAt,
                        completedAt = p?.completedAt,
                    )
                },
            )
        }
    }

    private fun QuizAttemptEntity.toDomain() = QuizAttempt(id, lessonId, score, totalQuestions, attemptedAt)
}
