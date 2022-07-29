package com.mksoftware101.personalnotes.ui.noteslist

import android.os.Build
import androidx.annotation.RequiresApi
import com.mksoftware101.personalnotes.domain.model.Note
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemDateViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.base.NotesListItemBaseViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Section(val date: String, val list: List<Note>)

class NotesListItemFactory {

    @RequiresApi(Build.VERSION_CODES.O)
    fun assemble(notesList: List<Note>): List<NotesListItemBaseViewModel> {
        val itemsList = mutableListOf<NotesListItemBaseViewModel>()

        val onlyDates = notesList.map {
            it.creationDate
        }
        val noDuplicates = onlyDates.distinct()

        val alltogether = noDuplicates.map { date ->
            val associatedNotes = notesList.filter { it.creationDate == date }
            Section(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), associatedNotes)
        }

        alltogether.map { section ->
            itemsList.add(NotesListItemDateViewModel(section.date))
            section.list.forEach {
                itemsList.add(NotesListItemViewModel(it.Id, it.title))
            }
        }
        return itemsList
    }
}