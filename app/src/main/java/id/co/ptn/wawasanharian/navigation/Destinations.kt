package id.co.ptn.wawasanharian.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import id.co.ptn.wawasanharian.R

/** Top-level tabs shown in the bottom navigation bar. */
enum class TopLevelDestination(
    val route: String,
    @param:StringRes val labelRes: Int,
    @param:DrawableRes val iconRes: Int,
) {
    HOME("home", R.string.nav_home, R.drawable.ic_nav_home),
    CURRICULUM("curriculum", R.string.nav_curriculum, R.drawable.ic_nav_curriculum),
    PROGRESS("progress", R.string.nav_progress, R.drawable.ic_nav_progress),
    SETTINGS("settings", R.string.nav_settings, R.drawable.ic_nav_settings),
}

object Routes {
    const val ARG_LESSON_ID = "lessonId"
    const val LESSON = "lesson/{$ARG_LESSON_ID}"
    const val QUIZ = "quiz/{$ARG_LESSON_ID}"

    fun lesson(lessonId: String) = "lesson/$lessonId"
    fun quiz(lessonId: String) = "quiz/$lessonId"
}
