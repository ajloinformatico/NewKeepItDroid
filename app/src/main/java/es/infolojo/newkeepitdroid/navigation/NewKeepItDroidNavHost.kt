package es.infolojo.newkeepitdroid.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents
import es.infolojo.newkeepitdroid.ui.screens.add.AddScreen
import es.infolojo.newkeepitdroid.ui.screens.home.HomeScreen
import es.infolojo.newkeepitdroid.ui.screens.search.SearchScreen
import es.infolojo.newkeepitdroid.ui.screens.update.UpdateScreen

private const val ANIMATION_TRANSITION_DURATION = 700

@Composable
fun NewKeepItDroidNavHost(
    modifier: Modifier,
    mainEvents: (MainEvents) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.Home.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(ANIMATION_TRANSITION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(ANIMATION_TRANSITION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(ANIMATION_TRANSITION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        }
    ) {
        composable(ScreensRoutes.Home.route) {
            HomeScreen(mainEvents = mainEvents, navHostController = navController)
        }
        composable(ScreensRoutes.Search.route) {
            SearchScreen(modifier = modifier, mainEvents = mainEvents)
        }
        composable(ScreensRoutes.Add.route) {
            AddScreen(modifier = modifier, mainEvents = mainEvents)
        }
        composable(
            route = ScreensRoutes.Update.routeWithArgument,
            arguments = listOf(navArgument(ScreensRoutes.Update.argumentName) {
                type = NavType.LongType
            })
        ) { navBackStackEntry ->
            val noteId =
                navBackStackEntry.arguments?.getLong(ScreensRoutes.Update.argumentName) ?: 0L
            UpdateScreen(modifier = modifier, noteId = noteId, mainEvents = mainEvents)
        }
    }
}
