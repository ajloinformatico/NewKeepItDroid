package es.infolojo.newkeepitdroid.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.domain.usecase.DeleteNoteUseCase
import es.infolojo.newkeepitdroid.domain.usecase.GetNotesUseCase
import es.infolojo.newkeepitdroid.domain.usecase.SearchNotesUseCase
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

/**
 * `SearchScreenViewModel` is a ViewModel responsible for managing the data and logic
 * related to the search screen. It interacts with use cases to fetch and delete notes,
 * and exposes state variables for UI updates.
 *
 * @property getNotesUseCase Use case for retrieving notes based on various criteria.
 * @property deleteNoteUseCase Use case for deleting a specific note.
 */
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val searchNotesUseCase: SearchNotesUseCase
) : ViewModel() {

    // region attr
    // states
    private val _notes = MutableStateFlow<List<NoteVO>>(emptyList())
    val searchText = mutableStateOf("")
    val notes: StateFlow<List<NoteVO>> = _notes.asStateFlow()

    // needed for remove
    val showAlertToRemove = mutableStateOf(false)
    var noteToRemove: NoteVO? = null

    // events
    private var mainEvents: ((MainEvents) -> Unit) = {}

    // nav
    private var navController: NavHostController? = null
    // endregion attr

    // region public
    /**
     * custom init. save [mainEvents] and clear removeState
     */
    fun init(mainEvents: ((MainEvents) -> Unit), navController: NavHostController?) {
        this.mainEvents = mainEvents
        this.navController = navController
        restartRemoveState()
        viewModelScope.launch(Dispatchers.IO) {
            searchNotes()
        }
    }

    /**
     * manage view events by reading [events] value
     */
    fun manageHomeScreenGridEvents(events: SearchScreenGridEvents) {
        when (events) {
            is SearchScreenGridEvents.Update -> navController?.navigate(
                ScreensRoutes.Update.createRoute(events.id)
            )

            is SearchScreenGridEvents.Delete -> {
                noteToRemove = events.noteVO
                showAlertToRemove.value = true
            }
        }
    }

    /**
     * dynamic update of search text by updating [searchText] state with given
     * [text] string
     */
    fun updateSearchText(text: String) {
        searchText.value = text
        launchPostDelayed {
            viewModelScope.launch(Dispatchers.IO) { searchNotes() }
        }
    }

    /**
     * remove selected note and restart remove states
     */
    fun removeSelectedNote() {
        val noteToRemoveCopy = noteToRemove?.copy()?.toBO()
        restartRemoveState()
        viewModelScope.launch(Dispatchers.IO) {
            noteToRemoveCopy?.let {
                deleteNoteUseCase(it)
            }
            searchNotes()
        }
    }

    fun restartRemoveState() {
        showAlertToRemove.value = false
        launchPostDelayed { noteToRemove = null }
    }
    // endregion public

    // region private
    /**
     * Do the search. if [searchText] is blank then do a simple search by date descend
     * If [searchText] is not blank then do the search. Steps:
     * - First search by title.
     * - After first search search by content.
     * - Then filter the notes found by content to remove the notes already found by title.
     * - Then unify notes and map to VO.
     */
    private suspend fun searchNotes() {
        (searchText.value.takeIf { it.isNotBlank() }?.let { textToSearch ->
            searchNotesUseCase(textToSearch)
        } ?: getNotesUseCase(SortOrder.DateDescend)).collect { notes ->
            _notes.value = notes.toVO()
        }
    }
    // endregion private
}
