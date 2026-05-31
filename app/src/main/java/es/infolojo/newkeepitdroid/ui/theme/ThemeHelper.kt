package es.infolojo.newkeepitdroid.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
        actionIconContentColor = getContentPrimaryColor()
    )
}
