package es.infolojo.newkeepitdroid.ui.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import dagger.hilt.android.AndroidEntryPoint
import es.infolojo.newkeepitdroid.R
import es.infolojo.newkeepitdroid.navigation.NewKeepItDroidNavHost
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents
import es.infolojo.newkeepitdroid.ui.screens.vo.UIMessagesVO
import es.infolojo.newkeepitdroid.ui.theme.NewKeepItDroidTheme
import es.infolojo.newkeepitdroid.utils.ToastMaker


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val keyBoardController = LocalSoftwareKeyboardController.current
            NewKeepItDroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewKeepItDroidNavHost(
                        modifier = Modifier.padding(innerPadding),
                        mainEvents = {
                            manageEvents(
                                event = it,
                                keyBoardController = keyBoardController
                            )
                        }
                    )
                }
            }
        }
    }

    private fun manageEvents(event: MainEvents, keyBoardController: SoftwareKeyboardController?) {
        when (event) {
            is MainEvents.ShowMessage -> ToastMaker.showMessage(
                this,
                when (event.message) {
                    UIMessagesVO.DATA_BASE_UPDATED -> getString(R.string.note_updated)
                    UIMessagesVO.DATABASE_NOTE_ADDED -> getString(R.string.note_saved)
                    UIMessagesVO.ERROR_NOTE_IN_DATABASE -> getString(R.string.note_already_in_database)
                    UIMessagesVO.NO_MESSAGE -> return
                }
            )
            is MainEvents.CustomMessage -> ToastMaker.showMessage(this, event.message)
            is MainEvents.HideKeyBoard -> {
                keyBoardController?.hide()
            }
        }
    }
}
