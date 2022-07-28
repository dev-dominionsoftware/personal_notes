package com.mksoftware101.personalnotes.domain.di

import com.mksoftware101.personalnotes.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllNotesUseCase(repository: NotesRepository) = GetAllNotesUseCase(repository)

    @Provides
    fun provideGetNoteByIdUseCase(repository: NotesRepository) = GetNoteByIdUseCase(repository)

    @Provides
    fun provideUpdateNoteUseCase(repository: NotesRepository) = UpdateNoteUseCase(repository)

    @Provides
    fun provideDeleteNoteUseCase(repository: NotesRepository) = DeleteNoteUseCase(repository)
}