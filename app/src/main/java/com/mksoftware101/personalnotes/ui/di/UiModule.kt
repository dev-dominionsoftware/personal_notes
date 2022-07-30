package com.mksoftware101.personalnotes.ui.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.common.Formatter
import com.mksoftware101.personalnotes.ui.noteslist.NotesListDateSectionFormatter
import com.mksoftware101.personalnotes.ui.noteslist.NotesListItemFactory
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Named("today")
    fun provideToday() = LocalDate.now()

    @Provides
    @Named("localizedTodayText")
    fun provideLocalizedTodayText(@ApplicationContext context: Context) =
        context.getText(R.string.today).toString()

    @Provides
    fun provideNotesListDateSectionFormatter(
        formatter: Formatter,
        @Named("today") today: LocalDate,
        @Named("localizedTodayText") localizedTodayText: String
    ) = NotesListDateSectionFormatter(formatter, today, localizedTodayText)

    @Provides
    fun provideNotesListItemFactory(formatter: NotesListDateSectionFormatter) =
        NotesListItemFactory(formatter)
}