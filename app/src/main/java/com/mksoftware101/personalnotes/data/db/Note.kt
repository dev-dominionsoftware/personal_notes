package com.mksoftware101.personalnotes.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val data: String
)
