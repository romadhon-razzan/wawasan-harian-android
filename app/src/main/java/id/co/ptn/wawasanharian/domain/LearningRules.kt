package id.co.ptn.wawasanharian.domain

/**
 * Pure learning rules shared by the app screens and the Home Screen widget.
 * Kept free of Android and database types so they can be unit-tested on the JVM.
 */
object LearningRules {

    /**
     * The lesson the user should continue with:
     * 1. the most recently opened lesson that is still in progress, otherwise
     * 2. the first lesson (in curriculum order) that is not completed, otherwise
     * 3. null — every lesson is completed.
     */
    fun currentLesson(stages: List<StageWithLessons>): LessonSummary? {
        val lessons = stages.flatMap { it.lessons }
        return lessons
            .filter { it.status == LessonStatus.IN_PROGRESS }
            .maxByOrNull { it.lastOpenedAt ?: Long.MIN_VALUE }
            ?: lessons.firstOrNull { it.status != LessonStatus.COMPLETED }
    }

    fun progressSummary(stages: List<StageWithLessons>, recentLimit: Int = 3): ProgressSummary {
        val lessons = stages.flatMap { it.lessons }
        val current = currentLesson(stages)
        val currentStage = when {
            current != null -> stages.first { it.stage.id == current.stageId }.stage
            else -> stages.lastOrNull()?.stage
        }
        return ProgressSummary(
            completedCount = lessons.count { it.status == LessonStatus.COMPLETED },
            totalCount = lessons.size,
            currentStage = currentStage,
            recentlyCompleted = lessons
                .filter { it.status == LessonStatus.COMPLETED }
                .sortedByDescending { it.completedAt ?: Long.MIN_VALUE }
                .take(recentLimit),
        )
    }

    /** Number of lessons completed at or after [sinceMillis]. Used for gentle weekly feedback, not streaks. */
    fun completedSince(stages: List<StageWithLessons>, sinceMillis: Long): Int =
        stages.flatMap { it.lessons }.count {
            it.status == LessonStatus.COMPLETED && (it.completedAt ?: Long.MIN_VALUE) >= sinceMillis
        }

    fun stageProgress(stage: StageWithLessons): StageProgress = StageProgress(
        stage = stage.stage,
        completed = stage.lessons.count { it.status == LessonStatus.COMPLETED },
        total = stage.lessons.size,
    )

    /** Scores [answers] (questionId → selected optionId). Unanswered questions count as incorrect. */
    fun scoreQuiz(questions: List<QuizQuestion>, answers: Map<String, String>): QuizResult =
        QuizResult(
            questions.map { question ->
                val correct = question.options.single { it.isCorrect }
                QuestionResult(
                    questionId = question.id,
                    selectedOptionId = answers[question.id],
                    correctOptionId = correct.id,
                )
            }
        )

    /** [attempts] in any order. Best = highest score ratio; ties go to the earliest attempt. */
    fun quizStats(attempts: List<QuizAttempt>): QuizStats {
        val latest = attempts.maxByOrNull { it.attemptedAt }
        val best = attempts
            .sortedBy { it.attemptedAt }
            .maxByOrNull { if (it.totalQuestions == 0) 0.0 else it.score.toDouble() / it.totalQuestions }
        return QuizStats(latest = latest, best = best)
    }
}
