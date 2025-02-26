package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.mappers.toDBO
import es.infolojo.newkeepitdroid.domain.repository.LocalRepository

/**
 * Use case responsible for inserting a new note into the local data source.
 *
 * This class encapsulates the logic of inserting a [NoteBO] (Business Object) into the
 * underlying local repository. It acts as an intermediary between the presentation layer
 * and the data layer, ensuring that the presentation layer does not directly interact
 * with the repository and deals with business objects instead of data objects.
 *
 * @property repository The [LocalRepository] instance responsible for interacting with the local data source.
 */
class InsertNoteUseCase(
    private val repository: LocalRepository
): suspend ((NoteBO) -> Long) {
    override suspend fun invoke(note: NoteBO): Long {
        return repository.insertNote(note.toDBO())
    }
}
