package es.infolojo.newkeepitdroid.ui.screens.vo

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO

fun List<NoteBO>.toVO(): List<NoteVO> = this.map { it.toVO() }

fun List<NoteVO>.toBO(): List<NoteBO> = this.map { it.toBO() }

fun NoteBO.toVO(): NoteVO = NoteVO(
    id = id ?: 0L,
    title = title,
    content = content,
    date = date
)

fun NoteVO.toBO(): NoteBO = NoteBO(
    id = id,
    title = title,
    content = content,
    date = date
)
