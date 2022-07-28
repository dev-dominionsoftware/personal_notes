package com.mksoftware101.personalnotes.ui.notedetails

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mksoftware101.personalnotes.domain.GetNoteByIdUseCase
import com.mksoftware101.personalnotes.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel
@Inject constructor(private val getNoteByIdUseCase: GetNoteByIdUseCase) : ViewModel() {

    val titleObservable = ObservableField("")
    val noteObservable = ObservableField("")

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

    }
}