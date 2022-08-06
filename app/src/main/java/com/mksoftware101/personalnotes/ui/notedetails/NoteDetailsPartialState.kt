package com.mksoftware101.personalnotes.ui.notedetails

sealed class NoteDetailsPartialState {
    object Init : NoteDetailsPartialState()
    object NoteHasChanged : NoteDetailsPartialState()
    object NoteHasNotChanged : NoteDetailsPartialState()
    data class NoteFetched(val isSuccess: Boolean) : NoteDetailsPartialState()
    data class EditNote(val isSuccess: Boolean) : NoteDetailsPartialState()
    data class CreateNote(val isSuccess: Boolean) : NoteDetailsPartialState()
    data class DeleteNote(val isSuccess: Boolean) : NoteDetailsPartialState()
}