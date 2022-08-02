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
    private val dao: NoteDao,
    private val converter: Converter
) : NotesRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return converter.convert(
            dao.getNoteEntityList()
        )
    }

    override suspend fun getNoteBy(Id: Long): Note {
        return converter.convert(
            dao.getNoteBy(Id)
        )
    }

    override suspend fun insert(note: Note) {
        dao.insert(
            converter.convert(note, autoIncrement = true)
        )
    }

    override suspend fun update(note: Note) {
        dao.update(
            converter.convert(note)
        )
    }

    override suspend fun delete(note: Note) {
        dao.delete(
            converter.convert(note)
        )
    }
}
