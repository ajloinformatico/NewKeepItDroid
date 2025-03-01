package es.infolojo.newkeepitdroid.utils

import android.os.Handler
import android.os.Looper

private const val DEFAULT_DURATION = 200L

fun launchPostDelayed(duration: Long = DEFAULT_DURATION, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        action()
    }, duration)
}
