package es.infolojo.newkeepitdroid.domain.sharedpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import es.infolojo.newkeepitdroid.domain.data.bo.SharedPreferencesBO
import es.infolojo.newkeepitdroid.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SharedPreferencesBO.PREFERENCES_DATA_STORE.value)

/**
 * Manages the application theme preferences using Jetpack DataStore.
 * Use SharedPreferencesBO business object as key values
 */
class ThemePreferences(private val context: Context) {

    private val themeModeKey = stringPreferencesKey(SharedPreferencesBO.THEME_PREFERENCE_KEY.value)

    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { preferences ->
        val name = preferences[themeModeKey] ?: ThemeMode.SYSTEM.name
        try {
            ThemeMode.valueOf(name)
        } catch (_: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[themeModeKey] = mode.name
        }
    }
}
