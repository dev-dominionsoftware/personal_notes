package com.mksoftware101.personalnotes.ui.noteslist.item

import com.mksoftware101.personalnotes.ui.noteslist.item.base.NotesListItemBaseViewModel

class NotesListItemViewModel(val id: Long, val title: String) : NotesListItemBaseViewModel() {
    val letter: String
        get() = title.take(1)
}