package com.mksoftware101.personalnotes.domain

import com.mksoftware101.personalnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    fun run(): Flow<List<Note>> {
        return notesRepository.getAllNotes()
    }
}