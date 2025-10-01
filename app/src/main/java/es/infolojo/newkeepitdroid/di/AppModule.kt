package es.infolojo.newkeepitdroid.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.infolojo.newkeepitdroid.db.NotesDB
import es.infolojo.newkeepitdroid.domain.repository.LocalRepository
import es.infolojo.newkeepitdroid.domain.usecase.DeleteNoteUseCase
import es.infolojo.newkeepitdroid.domain.usecase.GetNotesUseCase
import es.infolojo.newkeepitdroid.domain.usecase.InsertNoteUseCase
import es.infolojo.newkeepitdroid.domain.usecase.IsNoteAlReadyInDataBase
import es.infolojo.newkeepitdroid.domain.usecase.SearchNotesUseCase
import es.infolojo.newkeepitdroid.domain.usecase.UpdateNoteUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide the NotesDB instance
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): NotesDB = Room.databaseBuilder(
        context,
        NotesDB::class.java,
        NotesDB.DATABASE_NAME
    ).build()

    // Provide tha dao instance to interact with the database
    @Provides
    fun provideDao(db: NotesDB): LocalRepository = db.notesDao()

    // Provide the insert note use case
    @Provides
    fun provideInsertNoteUseCase(repository: LocalRepository): InsertNoteUseCase = InsertNoteUseCase(repository)

    // Provide the get notes use case
    @Provides
    fun provideGetNotesUseCase(repository: LocalRepository): GetNotesUseCase = GetNotesUseCase(repository)

    // Provide the update note use case
    @Provides
    fun provideUpdateNoteUseCase(repository: LocalRepository): UpdateNoteUseCase = UpdateNoteUseCase(repository)

    // Provide the delete note use case
    @Provides
    fun provideDeleteNoteUseCase(repository: LocalRepository): DeleteNoteUseCase = DeleteNoteUseCase(repository)

    // Provide the check note use case
    @Provides
    fun provideCheckNoteUseCase(repository: LocalRepository): IsNoteAlReadyInDataBase = IsNoteAlReadyInDataBase(repository)

    @Provides
    fun provideSearchNotesUseCase(getNotesUseCase: GetNotesUseCase): SearchNotesUseCase = SearchNotesUseCase(getNotesUseCase)
}
