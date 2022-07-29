package com.mksoftware101.personalnotes.data.di

import android.os.Build
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideDateTimeFormatter() = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @Provides
    fun provideConverter(formatter: DateTimeFormatter) = Converter(formatter)

    @Provides
    fun provideNotesRepository(noteDao: NoteDao, converter: Converter): NotesRepository =
        NotesRepositoryImpl(noteDao, converter)
}