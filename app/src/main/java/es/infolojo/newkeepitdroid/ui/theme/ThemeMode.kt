package es.infolojo.newkeepitdroid.ui.theme

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK;

    fun next(): ThemeMode = when (this) {
        SYSTEM -> LIGHT
        LIGHT -> DARK
        DARK -> SYSTEM
    }

    fun isDark(systemInDarkMode: Boolean): Boolean = when (this) {
        SYSTEM -> systemInDarkMode
        LIGHT -> false
        DARK -> true
    }
}
