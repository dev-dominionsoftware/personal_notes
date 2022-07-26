package com.mksoftware101.personalnotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mksoftware101.personalnotes.domain.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonalNotesViewModel
@Inject constructor(val getAllNotesUseCase: GetAllNotesUseCase) : ViewModel() {

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.run().collect {
                Timber.d("[d] size=${it.size}, notes=$it")
            }
        }
    }
}