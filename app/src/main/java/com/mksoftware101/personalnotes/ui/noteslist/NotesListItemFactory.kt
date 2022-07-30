package com.mksoftware101.personalnotes.ui.noteslist

import android.os.Build
import androidx.annotation.RequiresApi
import com.mksoftware101.personalnotes.domain.model.Note
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemDateViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.base.NotesListItemBaseViewModel
import javax.inject.Inject

class NotesListItemFactory
@Inject constructor(private val formatter: NotesListDateSectionFormatter) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun assemble(notesList: List<Note>): List<NotesListItemBaseViewModel> {
        val output = mutableListOf<NotesListItemBaseViewModel>()

        val dateToNotesMap = mutableMapOf<String, List<Note>>()
        val onlyDatesWithoutDuplicates =
            notesList
                .map { note -> note.creationDateTime.toLocalDate() }
                .distinct()

        onlyDatesWithoutDuplicates.map { date ->
            val associatedNotes = notesList.filter { note ->
                date == note.creationDateTime.toLocalDate()
            }
            dateToNotesMap.put(formatter.format(date), associatedNotes)
        }

        dateToNotesMap.forEach { (key, values) ->
            output.add(NotesListItemDateViewModel(key))
            values.forEach { note ->
                output.add(NotesListItemViewModel(note.Id, note.title))
            }
        }

        return output
    }
}