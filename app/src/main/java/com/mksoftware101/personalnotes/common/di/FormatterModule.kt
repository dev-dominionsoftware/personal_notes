package com.mksoftware101.personalnotes.common.di

import com.mksoftware101.personalnotes.common.Formatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class FormatterModule {

    @Provides
    fun provideFormatter() = Formatter()
}