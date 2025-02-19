package es.infolojo.newkeepitdroid.domain.mappers

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.data.dbo.NoteDBO
import java.util.Date

fun List<NoteDBO>.toBO(): List<NoteBO> = this.map { it.toBO() }

fun List<NoteBO>.toDBO(): List<NoteDBO> = this.map { it.toDBO() }

fun NoteDBO.toBO(): NoteBO = NoteBO(
    id = this.id,
    content = this.content,
    date = this.date.customRoomConverted(),
    title = this.title,
)

fun NoteBO.toDBO(): NoteDBO = NoteDBO(
    id = this.id ?: 0,
    content = this.content,
    date = this.date.customRoomConverted(),
    title = this.title
)

fun Date?.customRoomConverted(): Long = this?.time ?: 0L
fun Long?.customRoomConverted(): Date = this?.let(::Date) ?: Date()
