package es.infolojo.newkeepitdroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.infolojo.newkeepitdroid.MainEvents
import es.infolojo.newkeepitdroid.ui.screens.add.AddScreen
import es.infolojo.newkeepitdroid.ui.screens.home.HomeScreen
import es.infolojo.newkeepitdroid.ui.screens.search.SearchScreen
import es.infolojo.newkeepitdroid.ui.screens.update.UpdateScreen

@Composable
fun NewKeepItDroidNavHost(
    modifier: Modifier,
    mainEvents: (MainEvents) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.Home.route
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
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val noteId =
                navBackStackEntry.arguments?.getLong(ScreensRoutes.Update.argumentName) ?: 0L
            UpdateScreen(modifier = modifier, noteId = noteId, mainEvents = mainEvents)
        }
    }
}
