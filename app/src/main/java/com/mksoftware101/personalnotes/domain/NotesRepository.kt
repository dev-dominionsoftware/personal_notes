package com.mksoftware101.personalnotes.domain

import com.mksoftware101.personalnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteBy(Id: Long) : Note
    suspend fun insert(note: Note)
    suspend fun update(note: Note)
    suspend fun delete(note: Note)
}