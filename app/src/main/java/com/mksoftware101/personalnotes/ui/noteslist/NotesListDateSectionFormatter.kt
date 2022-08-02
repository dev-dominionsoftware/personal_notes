package com.mksoftware101.personalnotes.ui.noteslist

import com.mksoftware101.personalnotes.common.Formatter
import java.time.LocalDate
import javax.inject.Inject

class NotesListDateSectionFormatter @Inject constructor(
    private val formatter: Formatter,
    private val today: LocalDate,
    private val localizedTodayText: String
) {
    fun format(date: LocalDate) : String =
        if (date.isEqual(today)) {
            localizedTodayText
        } else {
            date.format(formatter.getDescriptiveFormatter())
        }

}