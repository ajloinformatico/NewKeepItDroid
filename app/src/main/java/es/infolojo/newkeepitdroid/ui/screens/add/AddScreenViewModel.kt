package es.infolojo.newkeepitdroid.ui.screens.add

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.usecase.InsertNoteUseCase
import es.infolojo.newkeepitdroid.domain.data.common.DateModel
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
    private val insertNoteUseCase: InsertNoteUseCase
): ViewModel() {
    // region attr

    // public attr
    val dateModel: DateModel
        get() = DateModel.build()

    // states
    var title by mutableStateOf("")
    var content by mutableStateOf("")

    // validations
    private var titleValidated: Boolean by mutableStateOf(false)
    private var contentValidated: Boolean by mutableStateOf(false)
    val buttonValidated: Boolean
        get() = titleValidated && contentValidated
    // endregion attr

    // region public methods
    fun insertNote() {
        viewModelScope.launch(Dispatchers.IO) {
            insertNoteUseCase(
                NoteBO(
                    title = title,
                    content = content,
                    date = dateModel.time
                )
            )
        }
    }

    /**
     * Update note title with given [newTitle] and validate it.
     */
    fun updateTitle(newTitle: String) {
        title = newTitle
        titleValidated = newTitle.validateTitle()
    }

    /**
     * Update note content with given [newContent] and validate it.
     */
    fun updateContent(newContent: String) {
        content = newContent
        contentValidated = newContent.validateContent()
    }
    // endregion public methods
}
