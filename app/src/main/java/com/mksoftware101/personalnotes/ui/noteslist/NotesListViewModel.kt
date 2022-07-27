package com.mksoftware101.personalnotes.ui.noteslist

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mksoftware101.personalnotes.BR
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.domain.GetAllNotesUseCase
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel
@Inject constructor(val getAllNotesUseCase: GetAllNotesUseCase) : ViewModel() {

    val items = ObservableArrayList<NotesListItemViewModel>()
    val itemBinding =
        ItemBinding.of<NotesListItemViewModel>(BR.viewModel, R.layout.view_noteslist_item)

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.run().collect {
                Timber.d("[d] size=${it.size}, notes=$it")
                it.map {
                    val item = NotesListItemViewModel(it.title)
                    items.add(item)
                }
            }
        }
    }
}