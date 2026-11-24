package id.co.ptn.wawasanharian.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Curriculum content. Content ids are stable strings (e.g. "s1-l1") so progress rows,
// which reference lessons by id, survive a content re-seed.

@Entity(tableName = "learning_stage")
data class LearningStageEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val sortOrder: Int,
)

@Entity(
    tableName = "lesson",
    foreignKeys = [ForeignKey(
        entity = LearningStageEntity::class,
        parentColumns = ["id"],
        childColumns = ["stageId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("stageId")],
)
data class LessonEntity(
    @PrimaryKey val id: String,
    val stageId: String,
    val title: String,
    val summary: String,
    /** 1–2 short sentences sized for the Home Screen widget. */
    val widgetTeaser: String,
    val estimatedMinutes: Int,
    val sortOrder: Int,
    val contentVersion: Int,
    val isPublished: Boolean,
)

@Entity(
    tableName = "lesson_section",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = ["id"],
        childColumns = ["lessonId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("lessonId")],
)
data class LessonSectionEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    /** [id.co.ptn.wawasanharian.domain.SectionKind] name. */
    val kind: String,
    val title: String,
    val body: String,
    val sortOrder: Int,
)

@Entity(
    tableName = "key_term",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = ["id"],
        childColumns = ["lessonId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("lessonId")],
)
data class KeyTermEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val term: String,
    val definition: String,
    val sortOrder: Int,
)

@Entity(
    tableName = "everyday_example",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = ["id"],
        childColumns = ["lessonId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("lessonId")],
)
data class EverydayExampleEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val text: String,
    val sortOrder: Int,
)

@Entity(
    tableName = "conversation_prompt",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = ["id"],
        childColumns = ["lessonId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("lessonId")],
)
data class ConversationPromptEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val prompt: String,
    val exampleResponse: String,
    val sortOrder: Int,
)

@Entity(
    tableName = "quiz_question",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = ["id"],
        childColumns = ["lessonId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("lessonId")],
)
data class QuizQuestionEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val question: String,
    val explanation: String,
    val sortOrder: Int,
)

@Entity(
    tableName = "quiz_option",
    foreignKeys = [ForeignKey(
        entity = QuizQuestionEntity::class,
        parentColumns = ["id"],
        childColumns = ["questionId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index("questionId")],
)
data class QuizOptionEntity(
    @PrimaryKey val id: String,
    val questionId: String,
    val label: String,
    val isCorrect: Boolean,
    val sortOrder: Int,
)

/** Everything needed to (re)write the curriculum content in one transaction. */
data class ContentBundle(
    val stages: List<LearningStageEntity>,
    val lessons: List<LessonEntity>,
    val sections: List<LessonSectionEntity>,
    val keyTerms: List<KeyTermEntity>,
    val examples: List<EverydayExampleEntity>,
    val prompts: List<ConversationPromptEntity>,
    val questions: List<QuizQuestionEntity>,
    val options: List<QuizOptionEntity>,
)
