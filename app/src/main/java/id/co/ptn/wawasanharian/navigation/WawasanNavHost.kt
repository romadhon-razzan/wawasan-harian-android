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
import id.co.ptn.wawasanharian.feature.curriculum.CurriculumRoute
import id.co.ptn.wawasanharian.feature.home.HomeRoute
import id.co.ptn.wawasanharian.feature.lesson.LessonRoute
import id.co.ptn.wawasanharian.feature.progress.ProgressRoute
import id.co.ptn.wawasanharian.feature.quiz.QuizRoute
import id.co.ptn.wawasanharian.feature.settings.SettingsRoute
import kotlinx.coroutines.flow.first

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
            // On a cold start (e.g. from the widget) this effect runs before NavHost has set its
            // graph; wait for the first back-stack entry so navigate() cannot crash.
            navController.currentBackStackEntryFlow.first()
            navController.navigate(Routes.lesson(pendingLessonId)) {
                // Re-opening from the widget replaces any lesson/quiz already on top.
                popUpTo(TopLevelDestination.HOME.route)
                launchSingleTop = true
            }
            onPendingLessonHandled()
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = TopLevelDestination.entries.any { it.route == currentRoute }

    fun openTab(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val openLesson: (String) -> Unit = { id -> navController.navigate(Routes.lesson(id)) }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    TopLevelDestination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = { openTab(destination) },
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
                HomeRoute(
                    onOpenLesson = openLesson,
                    onOpenCurriculum = { openTab(TopLevelDestination.CURRICULUM) },
                )
            }
            composable(TopLevelDestination.CURRICULUM.route) {
                CurriculumRoute(onOpenLesson = openLesson)
            }
            composable(TopLevelDestination.PROGRESS.route) {
                ProgressRoute(onOpenLesson = openLesson)
            }
            composable(TopLevelDestination.SETTINGS.route) {
                SettingsRoute()
            }
            composable(
                Routes.LESSON,
                arguments = listOf(navArgument(Routes.ARG_LESSON_ID) { type = NavType.StringType }),
            ) { entry ->
                val lessonId = entry.arguments?.getString(Routes.ARG_LESSON_ID).orEmpty()
                LessonRoute(
                    lessonId = lessonId,
                    onBack = { navController.popBackStack() },
                    onOpenLesson = { id ->
                        // Previous/next replaces the current lesson so Back returns to where the user came from.
                        navController.navigate(Routes.lesson(id)) {
                            popUpTo(Routes.LESSON) { inclusive = true }
                        }
                    },
                    onOpenQuiz = { id -> navController.navigate(Routes.quiz(id)) },
                )
            }
            composable(
                Routes.QUIZ,
                arguments = listOf(navArgument(Routes.ARG_LESSON_ID) { type = NavType.StringType }),
            ) { entry ->
                QuizRoute(
                    lessonId = entry.arguments?.getString(Routes.ARG_LESSON_ID).orEmpty(),
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}
