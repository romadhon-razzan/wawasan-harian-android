package id.co.ptn.wawasanharian

import android.content.Context
import id.co.ptn.wawasanharian.data.local.WawasanDatabase
import id.co.ptn.wawasanharian.data.repository.OfflineLearningRepository
import id.co.ptn.wawasanharian.data.seed.ContentSeeder
import id.co.ptn.wawasanharian.domain.LearningRepository

/**
 * Manual dependency container (no DI framework). Holds app-wide singletons so that the
 * app screens and the Home Screen widget share one database and one source of truth.
 */
class AppContainer(context: Context) {
    private val appContext: Context = context.applicationContext

    private val database: WawasanDatabase by lazy { WawasanDatabase.build(appContext) }

    private val contentSeeder: ContentSeeder by lazy { ContentSeeder(database.contentDao()) }

    val learningRepository: LearningRepository by lazy {
        OfflineLearningRepository(
            contentDao = database.contentDao(),
            progressDao = database.progressDao(),
            seeder = contentSeeder,
        )
    }
}
