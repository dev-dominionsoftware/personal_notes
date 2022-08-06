package com.mksoftware101.personalnotes.ui.notedetails

sealed class NoteDetailsPartialState {
    object OperationDoneSuccessfully : NoteDetailsPartialState()
    object NoteChanged : NoteDetailsPartialState()
    object NothingChanged : NoteDetailsPartialState()
    data class NoteFetched(val isSuccess: Boolean) : NoteDetailsPartialState()
    data class NoteEdited(val isSuccess: Boolean) : NoteDetailsPartialState()
    data class NoteCreated(val isSuccess: Boolean) : NoteDetailsPartialState()
}