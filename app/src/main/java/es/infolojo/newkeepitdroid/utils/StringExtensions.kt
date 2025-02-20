package es.infolojo.newkeepitdroid.utils

private const val TITLE_LIMIT = 30

fun String.validateTitle(): Boolean = this.validateContent() && this.length <= TITLE_LIMIT

fun String.validateContent(): Boolean = this.isNotBlank()

fun String.validateTitleOnUpdate(originalValue: String): Boolean =
    this.validateContent() && this.length <= TITLE_LIMIT && this.equals(originalValue, ignoreCase = true)

fun String.validateContentOnUpdate(originalValue: String): Boolean =
    this.validateContent() && this.equals(originalValue, ignoreCase = true)
