package com.mksoftware101.personalnotes.ui.noteslist

import com.mksoftware101.personalnotes.domain.model.Note
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemDateViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.base.NotesListItemBaseViewModel

class NotesListItemFactory {
    fun assemble(notesList: List<Note>): List<NotesListItemBaseViewModel> {
        val itemsList = mutableListOf<NotesListItemBaseViewModel>()
        itemsList.add(NotesListItemDateViewModel("10 July 2022"))
        notesList.map { itemsList.add(NotesListItemViewModel(it.Id, it.title)) }
        return itemsList
    }
}