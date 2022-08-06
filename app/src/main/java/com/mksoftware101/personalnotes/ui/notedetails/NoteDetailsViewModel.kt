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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private val _state = MutableLiveData<NotesDetailsState>()
    val state: LiveData<NotesDetailsState> = _state

    private var note: Note? = null

    private var tempTitle = ""
    private var tempNoteText = ""
    private var isNoteChangedLastTime: Boolean = false

    fun getNoteBy(Id: Long) {
        if (Id == -1L) {
            return
        }

        viewModelScope.launch {
            try {
                val note = getNoteByIdUseCase.run(Id)
                titleObservable.set(note.title)
                noteObservable.set(note.data)
                this@NoteDetailsViewModel.note = note
                reduce(NoteDetailsPartialState.NoteFetched(isSuccess = true))
            } catch (e: Exception) {
                reduce(NoteDetailsPartialState.NoteFetched(isSuccess = false))
                e.printStackTrace()
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
        if (note == null) {
            if (tempTitle.isNotEmpty()) {
                reduce(NoteDetailsPartialState.NoteChanged)
            } else {
                reduce(NoteDetailsPartialState.NothingChanged)
            }
        } else {
            if (tempTitle.isEmpty()) {
                reduce(NoteDetailsPartialState.NothingChanged)
                return
            }

            if (tempTitle != note!!.title || tempNoteText != note!!.data) {
                reduce(NoteDetailsPartialState.NoteChanged)
            } else {
                reduce(NoteDetailsPartialState.NothingChanged)
            }
        }
    }

    private fun reduce(partialState: NoteDetailsPartialState) {
        val currentState: NotesDetailsState = _state.value ?: NotesDetailsState.initialize()
        isNoteChangedLastTime = currentState.isNoteChanged
        when (partialState) {
            is NoteDetailsPartialState.NoteFetched -> {
                _state.value = currentState.copy(isNoteFetched = partialState.isSuccess)
            }
            is NoteDetailsPartialState.NoteChanged -> {
                if (!isNoteChangedLastTime) {
                    _state.value = currentState.copy(isNoteChanged = true)
                }
            }
            is NoteDetailsPartialState.NothingChanged -> {
                if (isNoteChangedLastTime) {
                    _state.value = currentState.copy(isNoteChanged = false)
                }
            }
        }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            try {
                if (note == null) {
                    val note = Note(
                        -1,
                        titleObservable.get() ?: "",
                        noteObservable.get() ?: "",
                        LocalDateTime.now(),
                        false
                    )
                    insertNoteUseCase.run(note)
//                    _state.value = NoteDetailsPartialState.OperationDoneSuccessfully
                } else {
                    note?.let { note ->
                        updateNoteUseCase.run(
                            note.copy(
                                title = titleObservable.get() ?: "",
                                data = noteObservable.get() ?: ""
                            )
                        )
//                        _state.value = NoteDetailsPartialState.OperationDoneSuccessfully
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            try {
                note?.let { note ->
                    deleteNoteUseCase.run(note)
//                    _state.value = NoteDetailsPartialState.OperationDoneSuccessfully
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}