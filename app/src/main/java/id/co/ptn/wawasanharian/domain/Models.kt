package id.co.ptn.wawasanharian.domain

/** Parts of a lesson (product spec §3). Diskusikan and Uji are prompts and quiz, not sections. */
enum class SectionKind { KENALI, KETAHUI, HUBUNGKAN, RANGKUM }

enum class LessonStatus { NOT_STARTED, IN_PROGRESS, COMPLETED }

data class LearningStage(
    val id: String,
    val title: String,
    val description: String,
    val order: Int,
)

data class LessonSummary(
    val id: String,
    val stageId: String,
    val title: String,
    val summary: String,
    val widgetTeaser: String,
    val estimatedMinutes: Int,
    /** 1-based position of the lesson in the whole curriculum. */
    val number: Int,
    val status: LessonStatus,
    val lastOpenedAt: Long?,
    val completedAt: Long?,
)

data class StageWithLessons(
    val stage: LearningStage,
    val lessons: List<LessonSummary>,
)

data class LessonSection(
    val id: String,
    val kind: SectionKind,
    val title: String,
    val body: String,
)

data class KeyTerm(val term: String, val definition: String)

data class ConversationPrompt(val prompt: String, val exampleResponse: String)

data class LessonDetail(
    val lesson: LessonSummary,
    val stage: LearningStage,
    val totalLessons: Int,
    val sections: List<LessonSection>,
    val keyTerms: List<KeyTerm>,
    val everydayExamples: List<String>,
    val conversationPrompts: List<ConversationPrompt>,
    val questionCount: Int,
    val previousLessonId: String?,
    val nextLessonId: String?,
)

data class QuizOption(val id: String, val label: String, val isCorrect: Boolean)

data class QuizQuestion(
    val id: String,
    val question: String,
    val explanation: String,
    val options: List<QuizOption>,
)

data class QuestionResult(
    val questionId: String,
    val selectedOptionId: String?,
    val correctOptionId: String,
) {
    val isCorrect: Boolean get() = selectedOptionId == correctOptionId
}

data class QuizResult(val results: List<QuestionResult>) {
    val score: Int get() = results.count { it.isCorrect }
    val total: Int get() = results.size
}

data class QuizAttempt(
    val id: Long,
    val lessonId: String,
    val score: Int,
    val totalQuestions: Int,
    val attemptedAt: Long,
)

data class QuizStats(val latest: QuizAttempt?, val best: QuizAttempt?)

data class StageProgress(val stage: LearningStage, val completed: Int, val total: Int) {
    val fraction: Float get() = if (total == 0) 0f else completed.toFloat() / total
}

data class ProgressSummary(
    val completedCount: Int,
    val totalCount: Int,
    /** Stage of the current lesson, or the last stage once everything is completed. */
    val currentStage: LearningStage?,
    val recentlyCompleted: List<LessonSummary>,
) {
    val fraction: Float get() = if (totalCount == 0) 0f else completedCount.toFloat() / totalCount
    val allCompleted: Boolean get() = totalCount > 0 && completedCount == totalCount
}
