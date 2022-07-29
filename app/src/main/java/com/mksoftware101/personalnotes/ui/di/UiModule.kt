package com.mksoftware101.personalnotes.ui.di

import com.mksoftware101.personalnotes.ui.noteslist.NotesListItemFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {
    @Provides
    fun provideNotesListItemFactory() = NotesListItemFactory()
}