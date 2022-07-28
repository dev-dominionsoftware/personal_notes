package com.mksoftware101.personalnotes.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.mksoftware101.personalnotes.BR
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.domain.GetAllNotesUseCase
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.OnNotesListItemClickListener
import com.mksoftware101.personalnotes.ui.noteslist.states.NotesListPartialState
import com.mksoftware101.personalnotes.ui.noteslist.states.NotesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel
@Inject constructor(val getAllNotesUseCase: GetAllNotesUseCase) : ViewModel() {

    init {
        getAllNotes()
    }

    private var _state = MutableLiveData<NotesListState>()
    val state: LiveData<NotesListState> = _state

    val listener = OnNotesListItemClickListener { item ->
        val onItemClickedPartialState = NotesListPartialState.OnItemClicked(item.id)
        reduce(onItemClickedPartialState)
    }

    val items = DiffObservableList(object : DiffUtil.ItemCallback<NotesListItemViewModel>() {
        override fun areItemsTheSame(
            oldItem: NotesListItemViewModel,
            newItem: NotesListItemViewModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotesListItemViewModel,
            newItem: NotesListItemViewModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

    })
    val itemBinding =
        ItemBinding.of<NotesListItemViewModel>(BR.viewModel, R.layout.view_noteslist_item)
            .bindExtra(BR.listener, listener)

    fun initialize() {
        reduce(NotesListPartialState.Init)
    }

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.run().collect { notesList ->
                val newItems = notesList.map { NotesListItemViewModel(it.Id, it.title) }
                items.update(newItems)
            }
        }
    }

    fun OnAddNewNoteClick() {
        reduce(NotesListPartialState.OnAddNewNoteClicked)
    }

    private fun reduce(partialState: NotesListPartialState) {
        val currentState = _state.value ?: NotesListState.initialize()
        when (partialState) {
            is NotesListPartialState.Init -> {
                _state.value = NotesListState.initialize()
            }
            is NotesListPartialState.OnItemClicked -> {
                _state.value =
                    currentState.copy(isItemClicked = true, itemClickedId = partialState.itemId)
            }
            is NotesListPartialState.OnAddNewNoteClicked -> {
                _state.value = NotesListState.initialize().copy(isAddNewNoteClicked = true)
            }
        }
    }
}