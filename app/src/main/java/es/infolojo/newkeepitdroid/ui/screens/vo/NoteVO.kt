package es.infolojo.newkeepitdroid.ui.screens.vo

import java.util.Date

data class NoteVO(
    val id: Long,
    val title: String,
    val content: String,
    val date: Date
)
