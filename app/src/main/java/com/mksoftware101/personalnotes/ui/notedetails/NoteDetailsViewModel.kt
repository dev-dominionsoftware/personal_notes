package com.mksoftware101.personalnotes.ui.notedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mksoftware101.personalnotes.domain.DeleteNoteUseCase
import com.mksoftware101.personalnotes.domain.GetNoteByIdUseCase
import com.mksoftware101.personalnotes.domain.InsertNoteUseCase
import com.mksoftware101.personalnotes.domain.UpdateNoteUseCase
import com.mksoftware101.personalnotes.domain.model.Note
import com.mksoftware101.personalnotes.ui.common.NotesListConstants.NOTE_ID_UNDEFINED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel
@Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    val titleObservable = ObservableField("")
    val noteObservable = ObservableField("")

    private val _state = MutableLiveData<NoteDetailsState>()
    val state: LiveData<NoteDetailsState> = _state

    private var note: Note? = null

    private var tempTitle = ""
    private var tempNoteText = ""

    fun initialize() {
        reduce(NoteDetailsPartialState.Init)
    }

    fun getNoteBy(Id: Long) {
        if (Id == NOTE_ID_UNDEFINED) {
            return
        }

        viewModelScope.launch {
            try {
                note = getNoteByIdUseCase.run(Id)
                updateObservables()
                reduce(NoteDetailsPartialState.NoteFetched(isSuccess = true))
            } catch (e: Exception) {
                reduce(NoteDetailsPartialState.NoteFetched(isSuccess = false))
                Timber.e(e)
            }
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                if (isNewNote()) {
                    createNewNote().also { note ->
                        insertNoteUseCase.run(note)
                    }
                    reduce(NoteDetailsPartialState.CreateNote(isSuccess = true))
                } else {
                    note?.let { note ->
                        updateNoteUseCase.run(
                            note.copy(
                                title = tempTitle,
                                data = tempNoteText
                            )
                        )
                        reduce(NoteDetailsPartialState.EditNote(isSuccess = true))
                    }
                }
            } catch (e: Exception) {
                if (isNewNote()) {
                    reduce(NoteDetailsPartialState.CreateNote(isSuccess = false))
                } else {
                    reduce(NoteDetailsPartialState.EditNote(isSuccess = false))
                }
                Timber.e(e)
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            try {
                note?.let { note ->
                    deleteNoteUseCase.run(note)
                    reduce(NoteDetailsPartialState.DeleteNote(isSuccess = true))
                }
            } catch (e: Exception) {
                reduce(NoteDetailsPartialState.DeleteNote(isSuccess = false))
                Timber.e(e)
            }
        }
    }

    fun onTitleChanged(newTitle: String) {
        tempTitle = newTitle
        notifyChanges()
    }

    fun onNoteTextChanged(newNoteText: String) {
        tempNoteText = newNoteText
        notifyChanges()
    }

    private fun notifyChanges() {
        if (isNewNote()) {
            // Title is mandatory
            if (isNewTitleEmpty()) {
                reduce(NoteDetailsPartialState.NoteHasNotChanged)
            } else {
                reduce(NoteDetailsPartialState.NoteHasChanged)
            }
        } else {
            if (isNewTitleEmpty()) {
                reduce(NoteDetailsPartialState.NoteHasNotChanged)
                return
            }

            if (isTitleOrNoteTextChange()) {
                reduce(NoteDetailsPartialState.NoteHasChanged)
            } else {
                reduce(NoteDetailsPartialState.NoteHasNotChanged)
            }
        }
    }

    private fun isNewNote() = note == null

    private fun isNewTitleEmpty() = tempTitle.isEmpty()

    private fun isTitleOrNoteTextChange() =
        note?.let { note -> tempTitle != note.title || tempNoteText != note.data } ?: false

    private fun updateObservables() {
        titleObservable.set(note?.title ?: "")
        noteObservable.set(note?.data ?: "")
    }

    private fun createNewNote() = Note(
        Id = -1,
        title = tempTitle,
        data = tempNoteText,
        creationDateTime = LocalDateTime.now(),
        isFavourite = false
    )

    private fun reduce(partialState: NoteDetailsPartialState) {
        val currentState: NoteDetailsState = _state.value ?: NoteDetailsState.initialize()
        when (partialState) {
            is NoteDetailsPartialState.Init -> {
                _state.value = NoteDetailsState.initialize()
            }
            is NoteDetailsPartialState.NoteFetched -> {
                _state.value = currentState.copy(isNoteFetched = partialState.isSuccess)
            }
            is NoteDetailsPartialState.NoteHasChanged -> {
                _state.value = currentState.copy(isNoteChanged = true)
            }
            is NoteDetailsPartialState.NoteHasNotChanged -> {
                _state.value = currentState.copy(isNoteChanged = false)
            }
            is NoteDetailsPartialState.CreateNote -> {
                _state.value = currentState.copy(isCreateNoteSuccessfully = partialState.isSuccess)
            }
            is NoteDetailsPartialState.EditNote -> {
                _state.value = currentState.copy(isEditNoteSuccessfully = partialState.isSuccess)
            }
            is NoteDetailsPartialState.DeleteNote -> {
                _state.value = currentState.copy(isDeleteNoteSuccessfully = partialState.isSuccess)
            }
        }
    }
}