package id.co.ptn.wawasanharian.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        LearningStageEntity::class,
        LessonEntity::class,
        LessonSectionEntity::class,
        KeyTermEntity::class,
        EverydayExampleEntity::class,
        ConversationPromptEntity::class,
        QuizQuestionEntity::class,
        QuizOptionEntity::class,
        UserLessonProgressEntity::class,
        QuizAttemptEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class WawasanDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
    abstract fun progressDao(): ProgressDao

    companion object {
        const val NAME = "wawasan.db"

        // Schema changes must ship with a Migration (schemas are exported to app/schemas)
        // so that user progress is never dropped.
        fun build(context: Context, name: String = NAME): WawasanDatabase =
            Room.databaseBuilder(context.applicationContext, WawasanDatabase::class.java, name)
                .build()
    }
}
