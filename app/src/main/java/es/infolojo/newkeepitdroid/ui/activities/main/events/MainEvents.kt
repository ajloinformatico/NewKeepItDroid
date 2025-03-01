package es.infolojo.newkeepitdroid.ui.activities.main.events

import es.infolojo.newkeepitdroid.ui.screens.vo.UIMessagesVO

sealed interface MainEvents {
    data class ShowMessage(val message: UIMessagesVO) : MainEvents
}
