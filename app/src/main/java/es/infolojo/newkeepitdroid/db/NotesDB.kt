package es.infolojo.newkeepitdroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import es.infolojo.newkeepitdroid.domain.data.dbo.NoteDBO
import es.infolojo.newkeepitdroid.domain.repository.LocalRepository

@Database(entities = [NoteDBO::class], version = 1, exportSchema = false)
abstract class NotesDB: RoomDatabase() {
    abstract fun notesDao(): LocalRepository

    companion object {
        const val DATABASE_NAME = "Notes"
    }
}
