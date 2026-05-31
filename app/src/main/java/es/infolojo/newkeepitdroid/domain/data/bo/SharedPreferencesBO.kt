package es.infolojo.newkeepitdroid.domain.data.bo

private const val PREFERENCES_DATA_STORE_VALUE = "settings"
private const val PREFERENCES_THEME_KEY_VALUE = "theme_mode"

/**
 * Business Object representing the keys and identifiers used for Shared Preferences
 * or DataStore operations within the application.
 *
 * @property value The string key used to store or retrieve data from persistence.
 */
enum class SharedPreferencesBO(val value: String) {
    PREFERENCES_DATA_STORE(value = PREFERENCES_DATA_STORE_VALUE),
    THEME_PREFERENCE_KEY(PREFERENCES_THEME_KEY_VALUE)
}
