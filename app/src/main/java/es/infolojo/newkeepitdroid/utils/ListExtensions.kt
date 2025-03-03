package es.infolojo.newkeepitdroid.utils

/** Get size of any list */
fun List<Any>?.getSize(default: Int = 0): Int = this?.size ?: default
