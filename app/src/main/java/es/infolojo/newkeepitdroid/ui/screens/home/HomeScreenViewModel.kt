package es.infolojo.newkeepitdroid.ui.screens.home

import androidx.compose.runtime.mutableIntStateOf
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
import es.infolojo.newkeepitdroid.utils.launchPostDelayed
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
    var dropMenuExpanded = mutableStateOf(false)
    var numberOfColumns = mutableIntStateOf(1)

    // needed for remove
    val showAlertToRemove = mutableStateOf(false)
    var noteToRemove: NoteVO? = null

    // events
    private var mainEvents: ((MainEvents) -> Unit) = {}

    // nav
    private var navController: NavHostController? = null

    // endregion attr

    // region public methods
    init {
        restartRemoveStates()
        restartDropMenuExpanded()
        viewModelScope.launch(Dispatchers.IO) {
            getNotes()
        }
    }

    fun init(mainEvents: ((MainEvents) -> Unit), navController: NavHostController?) {
        this.mainEvents = mainEvents
        this.navController = navController
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
     * Click in dropMenuIcon
     */
    fun clickInDropMenuIcon() {
        dropMenuExpanded.value = !dropMenuExpanded.value
    }

    /**
     * Restart the [dropMenuExpanded] state]
     */
    fun restartDropMenuExpanded() {
        dropMenuExpanded.value = false
    }

    /**
     * Filter by noteDate
     */
    fun filterByDate() {
        viewModelScope.launch(Dispatchers.IO) {
            getNotes()
            restartDropMenuExpanded()
        }
    }

    /**
     * Filter by noteTitleAscend
     */
    fun filterByTitleAscend() {
        viewModelScope.launch(Dispatchers.IO) {
            getNotes(SortOrder.TitleAscend)
            restartDropMenuExpanded()
        }
    }

    /**
     * Filter by noteTitleDescend
     */
    fun filterByTitleDescend() {
        viewModelScope.launch(Dispatchers.IO) {
            getNotes(SortOrder.TitleDescend)
            restartDropMenuExpanded()
        }
    }

    /**
     * Navigate to add screen
     */
    fun openAddScreen() {
        navController?.navigate(ScreensRoutes.Add.route)
    }

    /**
     * Navigate to search screen
     */
    fun openSearchScreen() {
        navController?.navigate(ScreensRoutes.Search.route)
    }

    /**
     * Number of columns
     */
    fun changeNumberOfColumns() {
        numberOfColumns.intValue = if (numberOfColumns.intValue == 1) {
            2
        } else {
            1
        }
    }

    /**
     * remove selected note and restart remove states
     */
    fun removeSelectedNote() {
        val noteToRemoveCopy = noteToRemove?.copy()?.toBO()
        restartRemoveStates()
        viewModelScope.launch(Dispatchers.IO) {
            noteToRemoveCopy?.let {
                deleteNoteUseCase(it)
            }
            getNotes()
        }
    }

    /**
     * restart remove states
     */
    fun restartRemoveStates() {
        showAlertToRemove.value = false
        launchPostDelayed {
            noteToRemove = null
        }
    }
    // endregion public methods

    // region private methods
    private suspend fun getNotes(sorterOrder: SortOrder = SortOrder.DateDescend) {
        getNotesUseCase(sorterOrder).collect {
            _notes.value = it.toVO()
        }
    }
    // endregion private methods
}
