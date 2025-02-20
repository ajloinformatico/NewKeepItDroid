package es.infolojo.newkeepitdroid.utils

import android.content.Context
import android.widget.Toast

object ToastMaker {
    fun showMessage(context: Context?, message: String, time: Int = Toast.LENGTH_SHORT) {
        context?.let {
            Toast.makeText(it, message, time).show()
        }
    }
}
