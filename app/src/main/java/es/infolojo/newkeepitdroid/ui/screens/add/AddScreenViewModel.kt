package es.infolojo.newkeepitdroid.ui.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.data.common.DateModel
import es.infolojo.newkeepitdroid.domain.usecase.InsertNoteUseCase
import es.infolojo.newkeepitdroid.domain.usecase.IsNoteAlReadyInDataBase
import es.infolojo.newkeepitdroid.domain.usecase.UpdateNoteUseCase
import es.infolojo.newkeepitdroid.navigation.ScreensRoutes
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents
import es.infolojo.newkeepitdroid.ui.screens.vo.UIMessagesVO
import es.infolojo.newkeepitdroid.utils.validateContent
import es.infolojo.newkeepitdroid.utils.validateTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Add Note screen.
 *
 * This ViewModel is responsible for handling the logic related to adding a new note.
 * It utilizes the [InsertNoteUseCase] to persist the note data.
 *
 * @property insertNoteUseCase The use case responsible for inserting a new note into the data layer.
 */
@HiltViewModel
class AddScreenViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val isNoteAlReadyInDataBase: IsNoteAlReadyInDataBase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel() {
    // region attr
    private var mainEvents: (MainEvents) -> Unit = {}

    // save the original id after add a note in the dataBase
    private var noteAddedId = 0L

    // Check if the note has been added to the dataBase after add the new note to make an update instead of an insert
    private val noteAdded: Boolean
        get() = noteAddedId != 0L
    val dateModel: DateModel
        get() = DateModel.build()

    // states
    var title by mutableStateOf("")
    var content by mutableStateOf("")

    // validations
    private var titleValidated: Boolean by mutableStateOf(false)
    private var contentValidated: Boolean by mutableStateOf(false)
    private var noteAlReadyInDataBase: Boolean by mutableStateOf(false)
    val buttonValidated: Boolean
        get() = titleValidated && contentValidated && !noteAlReadyInDataBase
    // endregion attr

    // region public methods
    /**
     * Init the viewModel by saving the [mainEvents] in a attr
     * and restarting all ui states to the original values
     */
    fun init(mainEvents: (MainEvents) -> Unit) {
        this.mainEvents = mainEvents
        titleValidated = false
        contentValidated = false
        noteAddedId = 0L
        noteAlReadyInDataBase = false
    }

    /**
     * Insert or update the note in the database
     * first create the new note with the states [title], [content] and [dateModel],
     * also use [noteAddedId] if we have added a note before, otherwise use null.
     * If we have to update we just update and switch io to the main thread to show a message.
     * If we add instead of update it will be added in the next insert, and we will save the id in [noteAddedId]
     * and we will update the state [noteAlReadyInDataBase] to false, so as not to do more inserts.
     */
    fun insertNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val newNote = NoteBO(
                id = noteAddedId.takeIf { it != 0L },
                title = title,
                content = content,
                date = dateModel.time
            )
            if (noteAdded) {
                updateNoteUseCase(newNote)
                viewModelScope.launch(Dispatchers.Main) {
                    mainEvents(MainEvents.HideKeyBoard)
                    mainEvents(MainEvents.ShowMessage(UIMessagesVO.DATA_BASE_UPDATED))
                    mainEvents(MainEvents.OnBackPressed(ScreensRoutes.Add))
                }
            } else {
                val newNoteAddedId = insertNoteUseCase(newNote)
                // update values to do not make more inserts in the dataBase
                noteAddedId = newNoteAddedId
                noteAlReadyInDataBase = false
                viewModelScope.launch(Dispatchers.Main) {
                    mainEvents(MainEvents.HideKeyBoard)
                    mainEvents(MainEvents.ShowMessage(UIMessagesVO.DATABASE_NOTE_ADDED))
                    mainEvents(MainEvents.OnBackPressed(ScreensRoutes.Add))
                }
            }
        }
    }

    /**
     * Update note title and content states
     */
    fun updateTitleAndContent(
        newTitle: String? = null,
        newContent: String? = null
    ) {
        restartNoteAlreadyInDataBase()
        updateTitle(newTitle ?: title)
        updateContent(newContent ?: content)
        checkIfNoteAlReadyInDataBase()
    }
    // endregion public methods

    // region private methods
    private fun restartNoteAlreadyInDataBase() {
        noteAlReadyInDataBase = false
    }

    /**
     * Check if the current note is in the dataBase in a IO coroutine only if we do not add any previous note
     * if the current note is in the dataBase update [noteAlReadyInDataBase] state value to true
     * and launch in main thread to show message by using [mainEvents]
     * If the current note is not in the dataBase update [noteAlReadyInDataBase] state value to false
     * and do not launch any message and do not change the thread
     */
    private fun checkIfNoteAlReadyInDataBase() {
        // this check will be done only for the first insert because in the update
        // it has no sense
        viewModelScope.takeIf { !noteAdded }?.launch(Dispatchers.IO) {
            isNoteAlReadyInDataBase(
                NoteBO(
                    title = title,
                    content = content,
                    date = dateModel.time
                )
            ) { result ->
                noteAlReadyInDataBase = result
                // launch main context to show message
                viewModelScope.takeIf {
                    result
                }?.launch(Dispatchers.Main) {
                    mainEvents(MainEvents.ShowMessage(UIMessagesVO.ERROR_NOTE_IN_DATABASE))
                }
            }
        }
    }

    /**
     * Update note title with given [newTitle] and validate it.
     */
    private fun updateTitle(newTitle: String) {
        titleValidated = newTitle.validateTitle()
        title = newTitle

    }

    /**
     * Update note content with given [newContent] and validate it.
     */
    private fun updateContent(newContent: String) {
        contentValidated = newContent.validateContent()
        content = newContent
    }
    // endregion private methods
}
