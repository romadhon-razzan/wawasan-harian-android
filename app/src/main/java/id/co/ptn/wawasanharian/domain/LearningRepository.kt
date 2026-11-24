package id.co.ptn.wawasanharian.domain

import kotlinx.coroutines.flow.Flow

/**
 * Single source of truth for curriculum content and learning progress,
 * used by both the app screens and the Home Screen widget.
 */
interface LearningRepository {

    /** All published stages and lessons, in curriculum order, with the user's progress. */
    fun observeCurriculum(): Flow<List<StageWithLessons>>

    /** Emits null when [lessonId] does not exist. */
    fun observeLessonDetail(lessonId: String): Flow<LessonDetail?>

    suspend fun getQuizQuestions(lessonId: String): List<QuizQuestion>

    fun observeQuizStats(lessonId: String): Flow<QuizStats>

    /** Marks a lesson as opened; a not-started lesson becomes in progress. Completed stays completed. */
    suspend fun markLessonOpened(lessonId: String)

    suspend fun markLessonCompleted(lessonId: String)

    /** Scores the quiz and stores the attempt. Never changes lesson completion. */
    suspend fun submitQuiz(lessonId: String, answers: Map<String, String>): QuizResult

    /** Clears all lesson progress and quiz attempts. Content is kept. */
    suspend fun resetProgress()
}
