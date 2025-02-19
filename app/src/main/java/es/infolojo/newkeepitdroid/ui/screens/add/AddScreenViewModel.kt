package es.infolojo.newkeepitdroid.ui.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.usecase.InsertNoteUseCase
import es.infolojo.newkeepitdroid.domain.data.common.DateModel
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
    var titleValidated: Boolean by mutableStateOf(false)
    var contentValidated: Boolean by mutableStateOf(false)
    val buttonValidated: Boolean
        get() = titleValidated && contentValidated
    // endregion attr

    // region public methods
    fun insertNote() {
        viewModelScope.launch {
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
        validateTitle()
    }

    /**
     * Update note content with given [newContent] and validate it.
     */
    fun updateContent(newContent: String) {
        content = newContent
        validateContent()
    }

    // endregion public methods

    // region private methods
    /**
     * Validates the title of the note and update titleValidated state accordingly.
     */
    private fun validateTitle(): Boolean {
        val result = title.isNotEmpty() && title.length < 30
        titleValidated = result
        return result
    }

    /**
     * Validates the content of the note and update contentValidated state accordingly.
     */
    private fun validateContent(): Boolean {
        val result = content.isNotEmpty()
        contentValidated = result
        return result
    }
    // endregion private methods
}
