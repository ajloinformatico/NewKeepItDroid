package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.sharedpreferences.ThemePreferences
import es.infolojo.newkeepitdroid.ui.theme.ThemeMode

class SetThemeModeUseCase(
    private val themePreferences: ThemePreferences
) : suspend (ThemeMode) -> Unit {
    override suspend fun invoke(mode: ThemeMode) {
        themePreferences.setThemeMode(mode)
    }
}
