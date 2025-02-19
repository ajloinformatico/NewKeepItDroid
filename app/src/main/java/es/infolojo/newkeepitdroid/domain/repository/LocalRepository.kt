package es.infolojo.newkeepitdroid.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import es.infolojo.newkeepitdroid.domain.data.dbo.NoteDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalRepository {
    @Insert
    fun insertNote(note: NoteDBO)

    @Query("SELECT * FROM notedbo order by date desc")
    fun getNotesByDateDesc(): Flow<List<NoteDBO>>

    @Query("SELECT * FROM notedbo order by date asc")
    fun getNotesByDateAscend(): Flow<List<NoteDBO>>

    @Query("SELECT * FROM notedbo order by title desc")
    fun getNotesBtTitleDesc(): Flow<List<NoteDBO>>

    @Query("SELECT * FROM notedbo order by title asc")
    fun getNotesBtTitleAscend(): Flow<List<NoteDBO>>

    @Query("SELECT * FROM notedbo WHERE id = :id")
    fun getNoteById(id: Int): NoteDBO?

    @Query("SELECT * FROM notedbo WHERE title LIKE '%' || :title || '%'")
    fun getNotesByTitleQuery(title: String): Flow<List<NoteDBO>>

    @Query("SELECT * FROM notedbo WHERE content LIKE '%' || :content || '%'")
    fun getNotesByContentQuery(content: String): Flow<List<NoteDBO>>

    @Update
    fun updateNote(note: NoteDBO)

    @Delete
    fun deleteNote(note: NoteDBO)
}
