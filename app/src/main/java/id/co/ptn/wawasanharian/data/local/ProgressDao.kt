package id.co.ptn.wawasanharian.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProgressDao {

    @Query("SELECT * FROM user_lesson_progress")
    abstract fun observeAll(): Flow<List<UserLessonProgressEntity>>

    @Query("SELECT * FROM user_lesson_progress WHERE lessonId = :lessonId")
    abstract suspend fun get(lessonId: String): UserLessonProgressEntity?

    @Upsert
    abstract suspend fun upsert(progress: UserLessonProgressEntity)

    @Insert
    abstract suspend fun insertAttempt(attempt: QuizAttemptEntity): Long

    @Query("SELECT * FROM quiz_attempt WHERE lessonId = :lessonId ORDER BY attemptedAt DESC")
    abstract fun observeAttempts(lessonId: String): Flow<List<QuizAttemptEntity>>

    @Transaction
    open suspend fun clearAll() {
        deleteProgress()
        deleteAttempts()
    }

    @Query("DELETE FROM user_lesson_progress") protected abstract suspend fun deleteProgress()
    @Query("DELETE FROM quiz_attempt") protected abstract suspend fun deleteAttempts()
}
