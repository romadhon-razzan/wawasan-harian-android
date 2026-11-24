package id.co.ptn.wawasanharian.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.feature.placeholder.PlaceholderScreen

/**
 * Root navigation. [pendingLessonId] is a lesson requested from outside the app
 * (e.g. the Home Screen widget); it is opened once and then reported via [onPendingLessonHandled].
 */
@Composable
fun WawasanNavHost(
    pendingLessonId: String?,
    onPendingLessonHandled: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    LaunchedEffect(pendingLessonId) {
        if (pendingLessonId != null) {
            navController.navigate(Routes.lesson(pendingLessonId)) { launchSingleTop = true }
            onPendingLessonHandled()
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = TopLevelDestination.entries.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    TopLevelDestination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            // The visible label already describes the item for TalkBack.
                            icon = { Icon(painterResource(destination.iconRes), contentDescription = null) },
                            label = { Text(stringResource(destination.labelRes)) },
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelDestination.HOME.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(TopLevelDestination.HOME.route) {
                PlaceholderScreen(
                    title = stringResource(R.string.nav_home),
                    actionLabel = stringResource(R.string.placeholder_open_first_lesson),
                    onAction = { navController.navigate(Routes.lesson("placeholder")) },
                )
            }
            composable(TopLevelDestination.CURRICULUM.route) {
                PlaceholderScreen(title = stringResource(R.string.nav_curriculum))
            }
            composable(TopLevelDestination.PROGRESS.route) {
                PlaceholderScreen(title = stringResource(R.string.nav_progress))
            }
            composable(TopLevelDestination.SETTINGS.route) {
                PlaceholderScreen(title = stringResource(R.string.nav_settings))
            }
            composable(
                Routes.LESSON,
                arguments = listOf(navArgument(Routes.ARG_LESSON_ID) { type = NavType.StringType }),
            ) { entry ->
                val lessonId = entry.arguments?.getString(Routes.ARG_LESSON_ID).orEmpty()
                PlaceholderScreen(
                    title = stringResource(R.string.placeholder_lesson_title, lessonId),
                    onBack = { navController.popBackStack() },
                )
            }
            composable(
                Routes.QUIZ,
                arguments = listOf(navArgument(Routes.ARG_LESSON_ID) { type = NavType.StringType }),
            ) {
                PlaceholderScreen(
                    title = stringResource(R.string.placeholder_quiz_title),
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}
