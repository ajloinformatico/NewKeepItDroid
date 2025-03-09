package es.infolojo.newkeepitdroid.ui.screens.search

import es.infolojo.newkeepitdroid.ui.screens.vo.NoteVO

sealed interface SearchScreenGridEvents {
    data class Delete(
        val noteVO: NoteVO
    ) : SearchScreenGridEvents

    data class Update(
        val id: Long
    ) : SearchScreenGridEvents
}
