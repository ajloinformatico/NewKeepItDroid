package es.infolojo.newkeepitdroid.domain.data.bo

import java.util.Date

data class NoteBO(
    val id: Int? = null,
    val title: String,
    val content: String,
    val date: Date
)
