package com.mksoftware101.personalnotes.data.converter

import android.os.Build
import androidx.annotation.RequiresApi
import com.mksoftware101.personalnotes.data.db.NoteEntity
import com.mksoftware101.personalnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class Converter @Inject constructor(private val formatter: DateTimeFormatter) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convert(entity: NoteEntity): Note = with(entity) {
        Note(
            id,
            title,
            data,
            LocalDateTime.parse(creationDate, formatter),
            isFavourite
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convert(flowEntities: Flow<List<NoteEntity>>): Flow<List<Note>> {
        return flowEntities.map { notesEntitiesList ->
            notesEntitiesList.map { noteEntity ->
                convert(noteEntity)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convert(note: Note, autoIncrement: Boolean = false): NoteEntity = with(note) {
        if (autoIncrement) {
            NoteEntity(
                title = title,
                data = data,
                creationDate = creationDateTime.format(formatter),
                isFavourite = isFavourite
            )

        } else {
            NoteEntity(Id, title, data, creationDateTime.format(formatter), isFavourite)
        }
    }
}