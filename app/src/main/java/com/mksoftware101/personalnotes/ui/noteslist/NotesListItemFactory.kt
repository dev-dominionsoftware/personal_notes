package com.mksoftware101.personalnotes.ui.noteslist

import android.os.Build
import androidx.annotation.RequiresApi
import com.mksoftware101.personalnotes.domain.model.Note
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemDateViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.base.NotesListItemBaseViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotesListItemFactory {

    @RequiresApi(Build.VERSION_CODES.O)
    fun assemble(notesList: List<Note>): List<NotesListItemBaseViewModel> {
        val itemsList = mutableListOf<NotesListItemBaseViewModel>()

        val output = mutableMapOf<String, List<Note>>()

        val onlyDatesWithoutDuplicates =
            notesList
                .map { note ->
                    with(note.creationDateTime) { LocalDate.of(year, month, dayOfMonth) }
                }
                .distinct()

        onlyDatesWithoutDuplicates.map { date ->
            val associatedNotes = notesList.filter { note ->
                date == note.creationDateTime.toLocalDate()
            }
            output.put(date.format(DateTimeFormatter.ISO_LOCAL_DATE), associatedNotes)
        }

        output.forEach { (key, values) ->
            itemsList.add(NotesListItemDateViewModel(key))
            values.forEach { note ->
                itemsList.add(NotesListItemViewModel(note.Id, note.title))
            }
        }

        return itemsList
    }
}