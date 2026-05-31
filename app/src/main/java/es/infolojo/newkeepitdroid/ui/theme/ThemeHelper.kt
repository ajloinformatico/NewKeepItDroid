package es.infolojo.newkeepitdroid.ui.theme

import android.view.View
import android.view.Window
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowInsetsControllerCompat

object ThemeHelper {

    @Composable
    fun getSurfaceBackGroundColor(): Color = if (LocalIsDarkTheme.current) Black else White

    @Composable
    fun getContentPrimaryColor(): Color = if (LocalIsDarkTheme.current) White else Black

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun getTopBarColors(): TopAppBarColors = TopAppBarColors(
        containerColor = getSurfaceBackGroundColor(),
        scrolledContainerColor = getSurfaceBackGroundColor(),
        navigationIconContentColor = getContentPrimaryColor(),
        titleContentColor = getContentPrimaryColor(),
        actionIconContentColor = getContentPrimaryColor(),
        subtitleContentColor = getContentPrimaryColor()
    )


    fun updateSystemBarsAppearance(view: View, window: Window, isDark: Boolean) {
        val controller = WindowInsetsControllerCompat(window, view)
        controller.isAppearanceLightStatusBars = !isDark
        controller.isAppearanceLightNavigationBars = !isDark
    }
}
