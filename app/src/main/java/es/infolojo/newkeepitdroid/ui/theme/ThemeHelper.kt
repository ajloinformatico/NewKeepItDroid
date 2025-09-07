package es.infolojo.newkeepitdroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * `ThemeHelper` is a utility object that provides color schemes and configurations
 * based on the current system's dark/light theme setting. It simplifies the process
 * of adapting UI colors to the user's preferred theme.
 */
object ThemeHelper {

    /**
     * Returns the background color for the surface in the app.
     * @return [Black] if system is in dark theme, [White] otherwise.
     */
    @Composable
    fun getSurfaceBackGroundColor(): Color = if (isSystemInDarkTheme()) Black else White

    /**
     * Returns the primary color for content in the app.
     * @return [White] if system is in dark theme, [Black] otherwise.
     */
    @Composable
    fun getContentPrimaryColor(): Color = if (isSystemInDarkTheme()) White else Black

    /**
     * Returns app default top bar colors.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun getTopBarColors(): TopAppBarColors = TopAppBarColors(
        containerColor = getSurfaceBackGroundColor(),
        scrolledContainerColor = getSurfaceBackGroundColor(),
        navigationIconContentColor = getContentPrimaryColor(),
        titleContentColor = getContentPrimaryColor(),
        actionIconContentColor = getContentPrimaryColor()
    )
}
