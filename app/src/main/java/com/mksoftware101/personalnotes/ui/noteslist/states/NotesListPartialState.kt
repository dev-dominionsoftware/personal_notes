package com.mksoftware101.personalnotes.ui.noteslist.states

sealed class NotesListPartialState {
    object Init : NotesListPartialState()
    data class OnItemClick(val itemId: Long) : NotesListPartialState()
    object OnAddNewNoteClick : NotesListPartialState()
}