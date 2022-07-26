package com.mksoftware101.personalnotes.ui

import androidx.lifecycle.ViewModel
import com.mksoftware101.personalnotes.domain.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalNotesViewModel
@Inject constructor(val getAllNotesUseCase: GetAllNotesUseCase) : ViewModel() {

    fun getAllNotes() {
        TODO("Not implemented yet")
    }
}