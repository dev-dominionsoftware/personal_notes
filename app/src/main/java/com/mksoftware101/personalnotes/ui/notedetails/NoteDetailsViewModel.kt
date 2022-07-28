package com.mksoftware101.personalnotes.ui.notedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mksoftware101.personalnotes.domain.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel
@Inject constructor(private val getNoteByIdUseCase: GetNoteByIdUseCase) : ViewModel() {

    fun getNoteBy(Id: Long) {
        if (Id == -1L) {
            return
        }

        viewModelScope.launch {
            try {
                val note = getNoteByIdUseCase.run(Id)
                Timber.d("[d] note=$note")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSaveClick() {

    }
}