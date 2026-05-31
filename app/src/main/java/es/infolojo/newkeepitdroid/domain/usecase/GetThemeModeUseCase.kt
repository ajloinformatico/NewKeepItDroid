package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.sharedpreferences.ThemePreferences
import es.infolojo.newkeepitdroid.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

class GetThemeModeUseCase(
    private val themePreferences: ThemePreferences
) : () -> Flow<ThemeMode> {
    override fun invoke(): Flow<ThemeMode> = themePreferences.themeMode
}
