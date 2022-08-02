package com.mksoftware101.personalnotes.data.converter

import com.mksoftware101.personalnotes.common.Formatter
import com.mksoftware101.personalnotes.data.db.NoteEntity
import com.mksoftware101.personalnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class Converter @Inject constructor(private val formatter: Formatter) {

    fun convert(entity: NoteEntity): Note = with(entity) {
        Note(
            id,
            title,
            data,
            LocalDateTime.parse(creationDate, formatter.getISODateTimeFormatter()),
            isFavourite
        )
    }

    fun convert(flowEntities: Flow<List<NoteEntity>>): Flow<List<Note>> {
        return flowEntities.map { notesEntitiesList ->
            notesEntitiesList.map { noteEntity ->
                convert(noteEntity)
            }
        }
    }

    fun convert(note: Note, autoIncrement: Boolean = false): NoteEntity = with(note) {
        if (autoIncrement) {
            NoteEntity(
                title = title,
                data = data,
                creationDate = creationDateTime.format(formatter.getISODateTimeFormatter()),
                isFavourite = isFavourite
            )

        } else {
            NoteEntity(Id, title, data, creationDateTime.format(formatter.getISODateTimeFormatter()), isFavourite)
        }
    }
}