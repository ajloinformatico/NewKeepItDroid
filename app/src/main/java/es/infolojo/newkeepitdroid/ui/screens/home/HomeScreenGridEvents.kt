package es.infolojo.newkeepitdroid.ui.screens.home

import es.infolojo.newkeepitdroid.ui.screens.vo.NoteVO

sealed class HomeScreenGridEvents(
    open val id: Long
) {
    data class Delete(
        override val id: Long,
        val noteVO: NoteVO
    ): HomeScreenGridEvents(id = id)

    data class Update(
        override val id: Long
    ): HomeScreenGridEvents(id = id)
}
