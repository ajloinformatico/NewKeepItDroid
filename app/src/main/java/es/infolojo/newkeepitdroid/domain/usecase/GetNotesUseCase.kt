package es.infolojo.newkeepitdroid.domain.usecase

import es.infolojo.newkeepitdroid.domain.data.bo.NoteBO
import es.infolojo.newkeepitdroid.domain.mappers.toBO
import es.infolojo.newkeepitdroid.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

sealed interface SortOrder {
    data object DateDescend : SortOrder
    data object DateAscend : SortOrder
    data object TitleDescend : SortOrder
    data object TitleAscend : SortOrder
    data class ById(val id: Long) : SortOrder
    data class ByTitle(val title: String) : SortOrder
    data class ByContent(val content: String) : SortOrder
}

/**
 * Get notes use case. It needs the [SortOrder] param to works
 * The way to call is for example:
 * getNotesUseCase(SortOrder.DateDescend) -> it will returns a flow of [NoteBO] ordered bay date descend
 * Other options are:
 * SortOrder.DateDescend -> returns the notes ordered by date descend
 * SortOrder.DateAscend -> returns the notes ordered by date ascend
 * SortOrder.TitleAscend -> returns the notes ordered by title ascend
 * OrderBy.TitleDescend -> returns the notes ordered by title descend
 * OrderBy.ById(id = 1) -> returns the note with id 1
 * OrderBy.ByTitle("title") -> returns the notes with title "title"
 * OrderBy.ByContent("content") -> returns the notes with content "content"
 */
class GetNotesUseCase(
    private val repository: LocalRepository
) : suspend ((SortOrder) -> Flow<List<NoteBO>>) {
    override suspend fun invoke(sorterOrder: SortOrder): Flow<List<NoteBO>> = when (sorterOrder) {
        is SortOrder.DateDescend -> repository.getNotesByDateDesc().map { it.toBO() }
        is SortOrder.ByContent -> repository.getNotesByContentQuery(sorterOrder.content)
            .map { it.toBO() }

        is SortOrder.ById -> flow {
            emit(
                repository.getNoteById(sorterOrder.id)?.toBO()?.let(::listOf) ?: emptyList()
            )
        }

        is SortOrder.ByTitle -> repository.getNotesByTitleQuery(sorterOrder.title).map { it.toBO() }
        is SortOrder.DateAscend -> repository.getNotesByDateAscend().map { it.toBO() }
        is SortOrder.TitleAscend -> repository.getNotesBtTitleAscend().map { it.toBO() }
        is SortOrder.TitleDescend -> repository.getNotesBtTitleDesc().map { it.toBO() }
    }
}
