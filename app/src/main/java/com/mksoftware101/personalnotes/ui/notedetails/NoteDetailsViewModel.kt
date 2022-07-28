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
import timber.log.Timber
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            try {
                if (note == null) {
                    val note = Note(-1, titleObservable.get() ?: "", noteObservable.get() ?: "")
                    insertNoteUseCase.run(note)
                    _state.value = NoteDetailsState.OperationDoneSuccessfully
                } else {
                    note?.let { note ->
                        updateNoteUseCase.run(
                            note.copy(
                                title = titleObservable.get() ?: "",
                                data = noteObservable.get() ?: ""
                            )
                        )
                        _state.value = NoteDetailsState.OperationDoneSuccessfully
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
                    _state.value = NoteDetailsState.OperationDoneSuccessfully
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}