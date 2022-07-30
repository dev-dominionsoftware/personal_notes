package com.mksoftware101.personalnotes.ui.noteslist

import android.os.Build
import androidx.annotation.RequiresApi
import com.mksoftware101.personalnotes.common.Formatter
import java.time.LocalDate
import javax.inject.Inject

class NotesListDateSectionFormatter @Inject constructor(
    private val formatter: Formatter,
    private val today: LocalDate,
    private val localizedTodayText: String
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun format(date: LocalDate) =
        if (date.isEqual(today)) {
            localizedTodayText
        } else {
            date.format(formatter.getDescriptiveFormatter())
        }

}