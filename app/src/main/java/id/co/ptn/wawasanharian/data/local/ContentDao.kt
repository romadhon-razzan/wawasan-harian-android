package id.co.ptn.wawasanharian.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ContentDao {

    @Query("SELECT COUNT(*) FROM lesson")
    abstract suspend fun lessonCount(): Int

    @Query("SELECT MIN(contentVersion) FROM lesson")
    abstract suspend fun minContentVersion(): Int?

    @Query("SELECT * FROM learning_stage ORDER BY sortOrder")
    abstract fun observeStages(): Flow<List<LearningStageEntity>>

    @Query("SELECT * FROM lesson WHERE isPublished = 1 ORDER BY sortOrder")
    abstract fun observePublishedLessons(): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lesson_section WHERE lessonId = :lessonId ORDER BY sortOrder")
    abstract suspend fun sections(lessonId: String): List<LessonSectionEntity>

    @Query("SELECT * FROM key_term WHERE lessonId = :lessonId ORDER BY sortOrder")
    abstract suspend fun keyTerms(lessonId: String): List<KeyTermEntity>

    @Query("SELECT * FROM everyday_example WHERE lessonId = :lessonId ORDER BY sortOrder")
    abstract suspend fun examples(lessonId: String): List<EverydayExampleEntity>

    @Query("SELECT * FROM conversation_prompt WHERE lessonId = :lessonId ORDER BY sortOrder")
    abstract suspend fun prompts(lessonId: String): List<ConversationPromptEntity>

    @Query("SELECT * FROM quiz_question WHERE lessonId = :lessonId ORDER BY sortOrder")
    abstract suspend fun questions(lessonId: String): List<QuizQuestionEntity>

    @Query("SELECT * FROM quiz_option WHERE questionId IN (:questionIds) ORDER BY sortOrder")
    abstract suspend fun options(questionIds: List<String>): List<QuizOptionEntity>

    /** Replaces all curriculum content atomically. Progress tables are untouched. */
    @Transaction
    open suspend fun replaceContent(bundle: ContentBundle) {
        // Delete children first so this does not depend on SQLite foreign-key enforcement.
        deleteOptions()
        deleteQuestions()
        deletePrompts()
        deleteExamples()
        deleteKeyTerms()
        deleteSections()
        deleteLessons()
        deleteStages()
        insertStages(bundle.stages)
        insertLessons(bundle.lessons)
        insertSections(bundle.sections)
        insertKeyTerms(bundle.keyTerms)
        insertExamples(bundle.examples)
        insertPrompts(bundle.prompts)
        insertQuestions(bundle.questions)
        insertOptions(bundle.options)
    }

    @Query("DELETE FROM quiz_option") protected abstract suspend fun deleteOptions()
    @Query("DELETE FROM quiz_question") protected abstract suspend fun deleteQuestions()
    @Query("DELETE FROM conversation_prompt") protected abstract suspend fun deletePrompts()
    @Query("DELETE FROM everyday_example") protected abstract suspend fun deleteExamples()
    @Query("DELETE FROM key_term") protected abstract suspend fun deleteKeyTerms()
    @Query("DELETE FROM lesson_section") protected abstract suspend fun deleteSections()
    @Query("DELETE FROM lesson") protected abstract suspend fun deleteLessons()
    @Query("DELETE FROM learning_stage") protected abstract suspend fun deleteStages()

    @Insert protected abstract suspend fun insertStages(items: List<LearningStageEntity>)
    @Insert protected abstract suspend fun insertLessons(items: List<LessonEntity>)
    @Insert protected abstract suspend fun insertSections(items: List<LessonSectionEntity>)
    @Insert protected abstract suspend fun insertKeyTerms(items: List<KeyTermEntity>)
    @Insert protected abstract suspend fun insertExamples(items: List<EverydayExampleEntity>)
    @Insert protected abstract suspend fun insertPrompts(items: List<ConversationPromptEntity>)
    @Insert protected abstract suspend fun insertQuestions(items: List<QuizQuestionEntity>)
    @Insert protected abstract suspend fun insertOptions(items: List<QuizOptionEntity>)
}
