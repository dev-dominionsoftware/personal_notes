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

    override suspend fun insert(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note) {
        TODO("Not yet implemented")
    }
}
