package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


/**
 * `SearchNotesUseCase` is a use case class responsible for searching notes based on a given text query.
 * It leverages the `GetNotesUseCase` to retrieve notes sorted by title and content,
 * and then combines these results to provide a comprehensive list of matching notes.
 *
 * The search prioritizes notes matching the query in their title, presenting them before
 * notes that match the query in their content. Duplicate notes (matching in both title and content) are avoided:
 * Steps:
 *      - First search by title.
 *      - After first search search by content.
 *      - Then filter the notes found by content to remove the notes already found by title.
 *      - Then unify notes and map to VO.
 *
 * @property getNotesUseCase The use case responsible for retrieving notes, allowing for sorting by different criteria.
 */
class SearchNotesUseCase(
    private val getNotesUseCase: GetNotesUseCase
) : suspend (String) -> Flow<List<NoteBO>> {
    override suspend fun invoke(textToSearch: String): Flow<List<NoteBO>> = flow {
        getNotesUseCase(SortOrder.ByTitle(textToSearch)).collect { notesByTitle ->
            getNotesUseCase(SortOrder.ByContent(textToSearch)).collect { notesByContent ->
                // save first notes found with title and then add after these notes all the notes found by content
                emit(
                    notesByTitle + notesByContent.filter {
                        !notesByTitle.contains(it)
                    }
                )

            }
        }
    }
}
