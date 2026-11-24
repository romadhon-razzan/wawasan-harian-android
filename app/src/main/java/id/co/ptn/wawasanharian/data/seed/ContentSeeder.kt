package id.co.ptn.wawasanharian.data.seed

import id.co.ptn.wawasanharian.data.local.ContentDao
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Makes sure the bundled curriculum is in the database before anything reads it.
 * Safe to call from the app and the widget at the same time; the check runs once per process.
 */
class ContentSeeder(
    private val contentDao: ContentDao,
    private val stages: List<SeedStage> = SeedCurriculum.stages,
    private val version: Int = SeedCurriculum.VERSION,
) {
    private val mutex = Mutex()

    @Volatile
    private var ready = false

    suspend fun ensureSeeded() {
        if (ready) return
        mutex.withLock {
            if (ready) return
            val expectedLessons = stages.sumOf { it.lessons.size }
            val needsSeed = contentDao.lessonCount() != expectedLessons ||
                (contentDao.minContentVersion() ?: 0) < version
            if (needsSeed) {
                contentDao.replaceContent(stages.toContentBundle(version))
            }
            ready = true
        }
    }
}
