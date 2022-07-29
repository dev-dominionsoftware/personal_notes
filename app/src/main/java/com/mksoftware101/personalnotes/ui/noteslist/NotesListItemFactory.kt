package com.mksoftware101.personalnotes.ui.noteslist

import com.mksoftware101.personalnotes.domain.model.Note
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel

class NotesListItemFactory {
    fun assemble(notesList: List<Note>): List<NotesListItemViewModel> {
        return notesList.map { NotesListItemViewModel(it.Id, it.title) }
    }
}