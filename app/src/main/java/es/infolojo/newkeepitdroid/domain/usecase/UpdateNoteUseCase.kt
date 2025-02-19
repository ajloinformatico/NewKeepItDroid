package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.mappers.toDBO
import es.infolojo.newkeepitdroid.domain.repository.LocalRepository

/**
 * `UpdateNoteUseCase` is a use case class responsible for updating an existing note in the local data source.
 *
 * It implements the `((NoteBO) -> Unit)` functional interface, meaning it can be invoked as a function
 * that takes a `NoteBO` (Business Object) as input and performs an operation without returning any value (Unit).
 *
 * @property localRepository The repository responsible for interacting with the local data source.
 *                           It should implement the `LocalRepository` interface.
 */
class UpdateNoteUseCase(
    private val localRepository: LocalRepository
) : suspend ((NoteBO) -> Unit) {
    override suspend fun invoke(note: NoteBO) {
        localRepository.updateNote(note.toDBO())
    }
}
