package com.mksoftware101.personalnotes.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val data: String?,
    @ColumnInfo(name = "creation_date") val creationDate: String,
    @ColumnInfo(name = "is_favourite") val isFavourite: Boolean,
)
