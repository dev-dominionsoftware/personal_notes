package com.mksoftware101.personalnotes.data

import com.mksoftware101.personalnotes.data.db.NoteDao
import com.mksoftware101.personalnotes.domain.NotesRepository
import com.mksoftware101.personalnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NotesRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getNoteEntityList().toNoteList()
    }

    override suspend fun getNoteBy(Id: Long): Note {
        return noteDao.getNoteBy(Id).toDomainNote()
    }

    override suspend fun insert(note: Note) {
        noteDao.insert(note.toAutoincrementEntity())
    }

    override suspend fun update(note: Note) {
        noteDao.update(note.toEntity())
    }

    override suspend fun delete(note: Note) {
        TODO("Not yet implemented")
    }
}
