package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.mappers.toBO
import es.infolojo.newkeepitdroid.domain.repository.LocalRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * `IsNoteAlReadyInDataBase` is a class responsible for checking if a given `NoteBO` already exists within the local database.
 *
 * It uses the `LocalRepository` to query the database based on the note's title and content.
 * It checks first if a note with the same title is inside the dataBase. If there is at least one, then checks if that note also have the same content.
 * If no note with same title is inside the data base, the repository will search if there is a note with the same content.
 * It implements the `suspend ((NoteBO) -> Boolean)` functional interface, enabling its usage as a lambda function.
 *
 * @property localRepository The `LocalRepository` instance used to interact with the local database. Injected via constructor injection.
 */
class IsNoteAlReadyInDataBase @Inject constructor(
    private val localRepository: LocalRepository
): suspend ((NoteBO, ((Boolean) -> Unit)) -> Unit) {

    override suspend fun invoke(note: NoteBO, result: (Boolean) -> Unit) {
        localRepository.getNotesByTitleQuery(note.title).map { it.toBO() }.collect { notes ->
            if (notes.areNoteContentInsideTheList(note)) {
                result(true)
            } else {
                localRepository.getNotesByContentQuery(note.content).map { it.toBO() }.collect { notesContent ->
                    result(notesContent.areNoteContentInsideTheList(note))
                }
            }
        }
    }

    private fun List<NoteBO>.areNoteContentInsideTheList(note: NoteBO): Boolean = any { it.areNoteContentTheSame(note) }

    private fun NoteBO.areNoteContentTheSame(note: NoteBO): Boolean =
        title.equals(note.title, ignoreCase = true) && content.equals(note.content, ignoreCase = true)
}
