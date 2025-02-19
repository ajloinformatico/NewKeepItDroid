package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.mappers.toDBO
import es.infolojo.newkeepitdroid.domain.repository.LocalRepository

/**
 * Use case responsible for deleting a note from the local data source.
 *
 * This class implements the [((NoteBO) -> Unit)] functional interface, allowing it to be
 * invoked as a function that takes a [NoteBO] (Business Object) and deletes the
 * corresponding note in the local data source.
 *
 * @property localRepository The repository providing access to the local data source.
 */
class DeleteNoteUseCase(
    private val localRepository: LocalRepository
) : suspend ((NoteBO) -> Unit) {
    override suspend fun invoke(note: NoteBO) {
        localRepository.deleteNote(note.toDBO())
    }
}
