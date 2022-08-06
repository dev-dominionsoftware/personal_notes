package com.mksoftware101.personalnotes.ui.notedetails

data class NoteDetailsState(
    val isNoteFetched: Boolean,
    val isNoteChanged: Boolean
) {
    companion object {
        fun initialize() = NoteDetailsState(isNoteFetched = false, isNoteChanged = false)
    }
}