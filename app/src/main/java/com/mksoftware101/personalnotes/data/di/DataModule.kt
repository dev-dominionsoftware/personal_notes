package com.mksoftware101.personalnotes.data.di

import com.mksoftware101.personalnotes.common.Formatter
import com.mksoftware101.personalnotes.data.NotesRepositoryImpl
import com.mksoftware101.personalnotes.data.converter.Converter
import com.mksoftware101.personalnotes.data.db.NoteDao
import com.mksoftware101.personalnotes.domain.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.time.format.DateTimeFormatter

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun provideConverter(formatter: Formatter) = Converter(formatter)

    @Provides
    fun provideNotesRepository(noteDao: NoteDao, converter: Converter): NotesRepository =
        NotesRepositoryImpl(noteDao, converter)
}