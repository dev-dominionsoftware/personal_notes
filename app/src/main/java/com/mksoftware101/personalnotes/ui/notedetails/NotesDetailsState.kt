package com.mksoftware101.personalnotes.ui.notedetails

data class NotesDetailsState(
    val isNoteFetched: Boolean,
    val isNoteChanged: Boolean
) {
    companion object {
        fun initialize() = NotesDetailsState(isNoteFetched = false, isNoteChanged = false)
    }
}