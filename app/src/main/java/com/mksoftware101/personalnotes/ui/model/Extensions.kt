package com.mksoftware101.personalnotes.ui.model

import com.mksoftware101.personalnotes.domain.model.Note

fun Note.toNoteUI() = NoteUI(Id, title, data, creationDateTime, isFavourite)

fun List<Note>.toNotesUIList() = map { it.toNoteUI() }