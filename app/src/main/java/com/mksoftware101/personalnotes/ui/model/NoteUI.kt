package com.mksoftware101.personalnotes.ui.model

import java.time.LocalDateTime

data class NoteUI(
    val id: Long,
    val title: String,
    val note: String?,
    val creationDateTime: LocalDateTime,
    val isFavourite: Boolean
)