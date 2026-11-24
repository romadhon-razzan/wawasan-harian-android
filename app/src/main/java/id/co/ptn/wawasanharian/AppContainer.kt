package id.co.ptn.wawasanharian

import android.content.Context

/**
 * Manual dependency container (no DI framework). Holds app-wide singletons so that the
 * app screens and the Home Screen widget share one source of truth.
 *
 * Room database, DataStore and repositories are added here in Phase 2.
 */
class AppContainer(context: Context) {
    val appContext: Context = context.applicationContext
}
