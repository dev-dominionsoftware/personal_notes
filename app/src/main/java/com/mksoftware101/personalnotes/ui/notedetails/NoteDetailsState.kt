package com.mksoftware101.personalnotes.ui.notedetails

data class NoteDetailsState(
    val isNoteFetched: Boolean?,
    val isNoteChanged: Boolean,
    val isOperationDone: Boolean?,
    val isCreateNoteSuccessfully: Boolean?,
    val isEditNoteSuccessfully: Boolean?,
    val isDeleteNoteSuccessfully: Boolean?
) {
    companion object {
        fun initialize() = NoteDetailsState.of()

        fun of(
            isNoteFetched: Boolean? = null,
            isNoteChanged: Boolean = false,
            isOperationDone: Boolean? = null,
            isCreateNoteSuccessfully: Boolean? = null,
            isEditNoteSuccessfully: Boolean? = null,
            isDeleteNoteSuccessfully: Boolean? = null
        ) = NoteDetailsState(
            isNoteFetched,
            isNoteChanged,
            isOperationDone,
            isCreateNoteSuccessfully,
            isEditNoteSuccessfully,
            isDeleteNoteSuccessfully
        )
    }
}