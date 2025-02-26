package es.infolojo.newkeepitdroid.ui.screens.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.MainEvents
import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.data.common.DateModel
import es.infolojo.newkeepitdroid.domain.usecase.GetNotesUseCase
import es.infolojo.newkeepitdroid.domain.usecase.IsNoteAlReadyInDataBase
import es.infolojo.newkeepitdroid.domain.usecase.SortOrder
import es.infolojo.newkeepitdroid.domain.usecase.UpdateNoteUseCase
import es.infolojo.newkeepitdroid.ui.screens.vo.UIMessagesVO
import es.infolojo.newkeepitdroid.utils.validateContent
import es.infolojo.newkeepitdroid.utils.validateTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNotesUseCase,
    private val isNoteAlReadyInDataBase: IsNoteAlReadyInDataBase,
): ViewModel() {
    // region attr
    private var originalNote: NoteBO? = null
    private var mainEvents: (MainEvents) -> Unit = {}

    // ui states
    var title by mutableStateOf("")
    var content by mutableStateOf("")

    // validations
    private var titleValidated by mutableStateOf(false)
    private var contentValidated by mutableStateOf(false)
    private var noteAlReadyInDataBase by mutableStateOf(false)
    val dateModel: DateModel
        get() = DateModel.build()

    val updatedValidated: Boolean
        get() = titleValidated && contentValidated && !noteAlReadyInDataBase
    // endregion attr

    // region public methods
    /**
     * Get note and update view by using mutableStates
     */
    fun init(noteId: Long, mainEvents: ((MainEvents) -> Unit)) {
        this.mainEvents = mainEvents
        viewModelScope.launch(Dispatchers.IO) {
            getNoteByIdUseCase(SortOrder.ById(noteId)).collect {
                originalNote = it.firstOrNull()
                updateTitleAndContent(
                    newTitle = originalNote?.title.orEmpty(),
                    newContent = originalNote?.content.orEmpty(),
                    onFirstLoad = true
                )
            }
        }
    }

    /**
     * Updates the title and/or content of a note.
     *
     * This function allows updating the title and content of a note, with optional parameters for each.
     * It also handles the special case of the first load, where the note is assumed to already exist in the database.
     *
     * @param newTitle The new title for the note. If null, the current title is retained.
     * @param newContent The new content for the note. If null, the current content is retained.
     * @param onFirstLoad A boolean indicating if this is the first time the note is being loaded.
     *                    If true, it's assumed the note already exists in the database.
     *                    If false, it implies an update operation and triggers a check for the note's existence in the database.
     *
     * Behavior:
     *  - **On First Load (`onFirstLoad = true`):**
     *    - Sets the `noteAlReadyInDataBase` flag to `true`, indicating the note is presumed to be in the database.
     *  - **On Subsequent Updates (`onFirstLoad = false`):**
     *    - Resets the state of `noteAlReadyInDataBase` by calling `restartNoteAlreadyInDataBase()`.
     *    - Calls `checkIfNoteAlReadyInDataBase()` after updating title and content to verify the note's presence or state in the database.
     *  - **Title and Content Update:**
     *    - Always updates both the title and content, even if only one of `newTitle` or `newContent` is provided.
     *    - If `newTitle` or `newContent` is `null`, it uses the current `title` or `content`, respectively.
     *    - Calls `updateTitle()` and `updateContent()` to process the title and content updates.
     */
    fun updateTitleAndContent(
        newTitle: String? = null,
        newContent: String? = null,
        onFirstLoad: Boolean = false
    ) {
        if (onFirstLoad) {
            // at the first load note is already in database
            noteAlReadyInDataBase = true
        } else {
            // if is not the first load restart sate of current content inside the database
            restartNoteAlreadyInDataBase()
        }
        // check both is needed because if we change only the title wr have to update also content validation.
        updateTitle(newTitle ?: title)
        updateContent(newContent ?: content)
        // if is not the first load check both with the title on update and check in database
        if (!onFirstLoad) checkIfNoteAlReadyInDataBase()
    }

    /**
     * Update note in the database
     */
    fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val newNote = NoteBO(
                id = originalNote?.id ?: 0,
                title = title,
                content = content,
                date = dateModel.time
            )
            updateNoteUseCase(newNote)
            // after the update we have to change the status to block the update
            originalNote = newNote
            noteAlReadyInDataBase = true
            // launch main context to show message
            viewModelScope.launch(Dispatchers.Main) {
                mainEvents(MainEvents.ShowMessage(UIMessagesVO.DATA_BASE_UPDATED))
            }
        }
    }
    // endregion public methods

    // region private methods
    private fun updateTitle(newTitle: String) {
        titleValidated = newTitle.validateTitle()
        title = newTitle
    }

    private fun updateContent(newContent: String) {
        contentValidated = newContent.validateContent()
        content = newContent
    }

    private fun restartNoteAlreadyInDataBase() {
        noteAlReadyInDataBase = false
    }

    /**
     * Check if the current note is in the dataBase in a IO coroutine
     * if the current note is in the dataBase update [noteAlReadyInDataBase] state value to true
     * and launch in main thread to show message by using [mainEvents]
     * If the current note is not in the dataBase update [noteAlReadyInDataBase] state value to false
     * and do not launch any message and do not change the thread
     */
    private fun checkIfNoteAlReadyInDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
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
    // endregion private methods
}
