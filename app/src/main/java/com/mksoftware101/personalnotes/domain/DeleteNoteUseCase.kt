package com.mksoftware101.personalnotes.domain

import com.mksoftware101.personalnotes.domain.model.Note
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend fun run(note: Note) {
        repository.delete(note)
    }
}