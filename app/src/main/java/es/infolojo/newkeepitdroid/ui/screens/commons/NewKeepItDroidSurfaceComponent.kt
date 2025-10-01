package es.infolojo.newkeepitdroid.ui.screens.commons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import es.infolojo.newkeepitdroid.ui.theme.ThemeHelper

/**
 * A custom surface component that provides a consistent layout structure for KeepItDroid screens.
 * It utilizes the [Scaffold] composable to provide slots for a top app bar, a floating action button,
 * a bottom bar, and the main content area.
 *
 * @param modifier The [Modifier] to be applied to the overall surface.
 * @param topBar A composable function that represents the content of the top app bar.
 *               Defaults to an empty composable.
 * @param floatingActionButton A composable function that represents the content of the
 *                             floating action button. Defaults to an empty composable.
 * @param bottomBar A composable function that represents the content of the bottom navigation bar.
 *                  Defaults to an empty composable.
 * @param content A composable function that represents the main content area of the screen.
 *                It receives [PaddingValues] representing the padding that should be applied to the
 *                content based on the presence of other components like the top app bar and
 *                bottom navigation bar.
 */
@Composable
fun NewKeepItDroidSurfaceComponent(modifier: Modifier = Modifier, topBar: @Composable () -> Unit = {}, floatingActionButton: @Composable () -> Unit = {}, bottomBar: @Composable () -> Unit = {}, content: @Composable (innerPadding: PaddingValues) -> Unit) {
    Scaffold(
       modifier = modifier,
       containerColor = ThemeHelper.getSurfaceBackGroundColor(),
       topBar = topBar,
       floatingActionButton = floatingActionButton,
       bottomBar = bottomBar,
   ) { innerPadding ->
       content(innerPadding)
   }
}
