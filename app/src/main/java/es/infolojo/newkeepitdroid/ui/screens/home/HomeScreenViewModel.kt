package es.infolojo.newkeepitdroid.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.domain.usecase.DeleteNoteUseCase
import es.infolojo.newkeepitdroid.domain.usecase.GetNotesUseCase
import es.infolojo.newkeepitdroid.domain.usecase.SortOrder
import es.infolojo.newkeepitdroid.navigation.ScreensRoutes
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents
import es.infolojo.newkeepitdroid.ui.screens.vo.NoteVO
import es.infolojo.newkeepitdroid.ui.screens.vo.toBO
import es.infolojo.newkeepitdroid.ui.screens.vo.toVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    // region attr

    // states
    private val _notes = MutableStateFlow<List<NoteVO>>(emptyList())
    val notes: StateFlow<List<NoteVO>> = _notes.asStateFlow()

    // needed for remove
    val showAlertToRemove = mutableStateOf(false)
    var noteToRemove: NoteVO? = null

    // events
    private var mainEvents: ((MainEvents) -> Unit) = {}

    // nav
    private var navController: NavHostController? = null

    // endregion attr

    // region public methods
    fun init(mainEvents: ((MainEvents) -> Unit), navController: NavHostController?) {
        restartRemoveStates()
        this.mainEvents = mainEvents
        this.navController = navController
        viewModelScope.launch(Dispatchers.IO) {
            getNotes()
        }
    }

    /**
     * manage events from grid (update and remove)
     */
    fun manageHomeScreenGridEvents(events: HomeScreenGridEvents) {
        when (events) {
            is HomeScreenGridEvents.Delete -> {
                // When remove update remove states to show a dialog
                noteToRemove = events.noteVO
                showAlertToRemove.value = true
            }
            is HomeScreenGridEvents.Update -> {
                navController?.navigate(ScreensRoutes.Update.createRoute(events.id))
            }
        }
    }

    /**
     * Navigate to add screen
     */
    fun openAddScreen() {
        navController?.navigate(ScreensRoutes.Add.route)
    }

    /**
     * remove selected note and restart remove states
     */
    fun removeSelectedNote() {
        viewModelScope.launch(Dispatchers.IO) {
            noteToRemove?.let {
                val noteToRemoveCopy = it.copy().toBO()
                restartRemoveStates()
                deleteNoteUseCase(noteToRemoveCopy)
                getNotes()
            } ?: restartRemoveStates()
        }
    }

    /**
     * restart remove states
     */
    fun restartRemoveStates() {
        showAlertToRemove.value = false
        noteToRemove = null
    }
    // endregion public methods

    // region private methods
    private suspend fun getNotes() {
        getNotesUseCase(SortOrder.DateDescend).collect {
            _notes.value = it.toVO()
        }
    }
    // endregion private methods
}
