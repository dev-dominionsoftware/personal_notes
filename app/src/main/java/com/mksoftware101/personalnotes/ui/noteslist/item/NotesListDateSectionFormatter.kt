package com.mksoftware101.personalnotes.ui.noteslist.item

import com.mksoftware101.personalnotes.common.Formatter
import java.time.LocalDate
import javax.inject.Inject

/**
 * Format list section like date section
 *
 * All notes contains the same dates are listed under section.
 */
class NotesListDateSectionFormatter @Inject constructor(
    private val formatter: Formatter,
    private val today: LocalDate,
    private val localizedTodayText: String
) {
    fun format(date: LocalDate): String =
        if (date.isEqual(today)) {
            localizedTodayText
        } else {
            date.format(formatter.getDescriptiveFormatter())
        }

}