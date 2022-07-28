package com.mksoftware101.personalnotes.ui.noteslist.states

data class NotesListState(
    val isLoading: Boolean,
    val isItemClicked: Boolean,
    val itemClickedId: Long?
) {
    companion object {
        fun of(isLoading: Boolean, isClicked: Boolean, clickedItemId: Long?) =
            NotesListState(isLoading, isClicked, clickedItemId)

        fun initialize() = of(isLoading = false, isClicked = false, clickedItemId = null)
    }
}