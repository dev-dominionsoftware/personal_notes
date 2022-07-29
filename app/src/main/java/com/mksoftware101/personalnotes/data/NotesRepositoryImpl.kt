package com.mksoftware101.personalnotes.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.mksoftware101.personalnotes.data.converter.Converter
import com.mksoftware101.personalnotes.data.db.NoteDao
import com.mksoftware101.personalnotes.domain.NotesRepository
import com.mksoftware101.personalnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val converter: Converter
) : NotesRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAllNotes(): Flow<List<Note>> {
        return converter.convert(
            noteDao.getNoteEntityList()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNoteBy(Id: Long): Note {
        return converter.convert(
            noteDao.getNoteBy(Id)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insert(note: Note) {
        noteDao.insert(
            converter.convert(note, autoIncrement = true)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun update(note: Note) {
        noteDao.update(
            converter.convert(note)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun delete(note: Note) {
        noteDao.delete(
            converter.convert(note)
        )
    }
}
