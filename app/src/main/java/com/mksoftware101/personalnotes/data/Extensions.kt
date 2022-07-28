package com.mksoftware101.personalnotes.data

import com.mksoftware101.personalnotes.data.db.NoteEntity
import com.mksoftware101.personalnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<NoteEntity>>.toNoteList(): Flow<List<Note>> {
    return this.map { list ->
        list.map {
            Note(it.id, it.title, it.data)
        }
    }
}

fun NoteEntity.toDomainNote() = Note(id, title, data)

fun Note.toEntity() = NoteEntity(Id, title, data)