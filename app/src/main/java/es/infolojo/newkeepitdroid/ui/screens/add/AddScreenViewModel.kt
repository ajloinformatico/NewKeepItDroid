package es.infolojo.newkeepitdroid.ui.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.MainEvents
import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.data.common.DateModel
import es.infolojo.newkeepitdroid.domain.usecase.InsertNoteUseCase
import es.infolojo.newkeepitdroid.domain.usecase.IsNoteAlReadyInDataBase
import es.infolojo.newkeepitdroid.domain.usecase.UpdateNoteUseCase
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
    private var noteAddedId = 0L
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
    fun init(mainEvents: (MainEvents) -> Unit) {
        this.mainEvents = mainEvents
        titleValidated = false
        contentValidated = false
        noteAlReadyInDataBase = false
    }

    /**
     * In a IO coroutine create a new note by using current [title] and [content] and [dateModel]
     * states, with this new note by using [insertNoteUseCase] save it in the dataBase
     * after that update [noteAdded] is true because we do not want to check more this note
     * because we have a new note in the dataBase with the same values of [title] and [content]
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
                    mainEvents(MainEvents.ShowMessage(UIMessagesVO.DATA_BASE_UPDATED))
                }
            } else {
                val newNoteAddedId = insertNoteUseCase(newNote)
                noteAddedId = newNoteAddedId
                noteAlReadyInDataBase = false
                viewModelScope.launch(Dispatchers.Main) {
                    mainEvents(MainEvents.ShowMessage(UIMessagesVO.DATABASE_NOTE_ADDED))
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
