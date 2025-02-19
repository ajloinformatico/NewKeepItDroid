package es.infolojo.newkeepitdroid.domain.repository

import es.infolojo.newkeepitdroid.domain.data.dbo.NoteDBO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val notesDao: LocalRepository
): LocalRepository {
    override fun insertNote(note: NoteDBO) {
        notesDao.insertNote(note)
    }

    override fun getNotesByDateDesc(): Flow<List<NoteDBO>> = notesDao.getNotesByDateDesc()

    override fun getNotesByDateAscend(): Flow<List<NoteDBO>> = notesDao.getNotesByDateAscend()

    override fun getNotesBtTitleDesc(): Flow<List<NoteDBO>> = notesDao.getNotesBtTitleDesc()

    override fun getNotesBtTitleAscend(): Flow<List<NoteDBO>> = notesDao.getNotesBtTitleAscend()

    override fun getNoteById(id: Int): NoteDBO? = notesDao.getNoteById(id)

    override fun getNotesByTitleQuery(title: String): Flow<List<NoteDBO>> = notesDao.getNotesByTitleQuery(title)

    override fun getNotesByContentQuery(content: String): Flow<List<NoteDBO>> = notesDao.getNotesByTitleQuery(content)

    override fun updateNote(note: NoteDBO) = notesDao.updateNote(note)

    override fun deleteNote(note: NoteDBO) = notesDao.deleteNote(note)
}
