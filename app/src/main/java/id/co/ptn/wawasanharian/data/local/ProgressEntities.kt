package id.co.ptn.wawasanharian.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// User progress. Intentionally no foreign key to `lesson`: replacing content on a
// content-version upgrade must never cascade-delete the user's progress.

@Entity(tableName = "user_lesson_progress")
data class UserLessonProgressEntity(
    @PrimaryKey val lessonId: String,
    /** [id.co.ptn.wawasanharian.domain.LessonStatus] name. */
    val status: String,
    val startedAt: Long?,
    val completedAt: Long?,
    val lastOpenedAt: Long?,
)

@Entity(tableName = "quiz_attempt", indices = [Index("lessonId")])
data class QuizAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lessonId: String,
    val score: Int,
    val totalQuestions: Int,
    val attemptedAt: Long,
)
