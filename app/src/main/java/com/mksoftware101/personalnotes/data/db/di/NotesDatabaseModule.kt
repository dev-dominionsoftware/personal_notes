package com.mksoftware101.personalnotes.data.db.di

import android.content.Context
import androidx.room.Room
import com.mksoftware101.personalnotes.BuildConfig
import com.mksoftware101.personalnotes.data.db.NoteDao
import com.mksoftware101.personalnotes.data.db.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesDatabaseModule {

    @Provides
    @Named("databaseName")
    fun provideDatabaseName() = "PersonalNotesDatabase"

    @Provides
    @Singleton
    fun provideNotesDatabase(
        @ApplicationContext appContext: Context,
        @Named("databaseName") databaseName: String
    ): NotesDatabase {
        return if (BuildConfig.DEBUG) {
            Room.databaseBuilder(appContext, NotesDatabase::class.java, databaseName)
                .createFromAsset("/database/personal_notes.db")
                .build()
        } else {
            Room.databaseBuilder(appContext, NotesDatabase::class.java, databaseName).build()
        }
    }

    @Provides
    fun provideNoteDao(database: NotesDatabase): NoteDao = database.getNoteDao()
}