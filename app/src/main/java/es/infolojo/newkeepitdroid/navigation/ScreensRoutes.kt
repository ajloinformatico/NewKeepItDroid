package es.infolojo.newkeepitdroid.navigation

sealed class ScreensRoutes(val route: String) {
    data object Home : ScreensRoutes(HOME_ROUTE)
    data object Search : ScreensRoutes(SEARCH_ROUTE)
    data object Add : ScreensRoutes(ADD_ROUTE)
    data object Update : ScreensRoutes(UPDATE_ROUTE) {
        const val routeWithArgument = "$UPDATE_ROUTE/{$NOTE_ID_ARGUMENT}"
        const val argumentName = NOTE_ID_ARGUMENT
        fun createRoute(noteId: Long) = "$UPDATE_ROUTE/$noteId"
    }

    companion object {
        private const val NOTE_ID_ARGUMENT = "noteId"

        const val HOME_ROUTE = "home"
        const val SEARCH_ROUTE = "search"
        const val ADD_ROUTE = "add"
        const val UPDATE_ROUTE = "update"
    }
}
