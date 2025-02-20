package es.infolojo.newkeepitdroid.ui.screens.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.data.common.DateModel
import es.infolojo.newkeepitdroid.domain.usecase.GetNotesUseCase
import es.infolojo.newkeepitdroid.domain.usecase.SortOrder
import es.infolojo.newkeepitdroid.domain.usecase.UpdateNoteUseCase
import es.infolojo.newkeepitdroid.utils.validateContentOnUpdate
import es.infolojo.newkeepitdroid.utils.validateTitleOnUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNotesUseCase
): ViewModel() {
    // region attr
    private var originalNote: NoteBO? = null

    // ui states
    var title by mutableStateOf("")
    var content by mutableStateOf("")

    // validations
    private var titleValidated by mutableStateOf(false)
    private var contentValidated by mutableStateOf(false)
    val dateModel: DateModel
        get() = DateModel.build()

    val updatedValidated: Boolean
        get() = titleValidated && contentValidated
    // endregion attr

    /**
     * Get note and update view by using mutableStates
     */
    fun init(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getNoteByIdUseCase(SortOrder.ById(noteId)).collect {
                originalNote = it.firstOrNull()
                updateTitle(originalNote?.title.orEmpty())
                updateContent(originalNote?.content.orEmpty())
            }
        }
    }


    fun updateTitle(newTitle: String) {
        title = newTitle
        titleValidated = newTitle.validateTitleOnUpdate(newTitle)

    }

    fun updateContent(newContent: String) {
        content = newContent
        contentValidated = newContent.validateContentOnUpdate(newContent)
    }


    /**
     * Update note in the database
     */
    fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase(
                NoteBO(
                    id = originalNote?.id ?: 0,
                    title = title,
                    content = content,
                    date = dateModel.time
                )
            )
        }
    }
}
