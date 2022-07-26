package com.mksoftware101.personalnotes.domain.di

import com.mksoftware101.personalnotes.domain.GetAllNotesUseCase
import com.mksoftware101.personalnotes.domain.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllNotesUseCase(repository: NotesRepository) = GetAllNotesUseCase(repository)
}