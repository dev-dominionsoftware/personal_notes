package com.mksoftware101.personalnotes.ui.noteslist.states

sealed class NotesListPartialState {
    object Init : NotesListPartialState()
    data class OnItemClicked(val itemId: Long) : NotesListPartialState()
    object OnAddNewNoteClicked : NotesListPartialState()
}