package com.mksoftware101.personalnotes.ui.noteslist.states

data class NotesListState(
    val isLoading: Boolean,
    val isItemClicked: Boolean,
    val itemClickedId: Long?,
    val isAddNewNoteClicked: Boolean,
) {
    companion object {
        fun of(
            isLoading: Boolean,
            isClicked: Boolean,
            clickedItemId: Long?,
            isAddNewNoteClicked: Boolean
        ) =
            NotesListState(isLoading, isClicked, clickedItemId, isAddNewNoteClicked)

        fun initialize() = of(isLoading = false, isClicked = false, clickedItemId = null, isAddNewNoteClicked = false)
    }
}