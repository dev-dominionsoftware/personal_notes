package com.mksoftware101.personalnotes.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.mksoftware101.personalnotes.BR
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.domain.GetAllNotesUseCase
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemDateViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.OnNotesListItemClickListener
import com.mksoftware101.personalnotes.ui.noteslist.item.base.NotesListItemBaseViewModel
import com.mksoftware101.personalnotes.ui.noteslist.states.NotesListPartialState
import com.mksoftware101.personalnotes.ui.noteslist.states.NotesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel
@Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val notesListItemFactory: NotesListItemFactory
) : ViewModel() {

    private var _state = MutableLiveData<NotesListState>()
    val state: LiveData<NotesListState> = _state

    val itemsList =
        DiffObservableList(object : DiffUtil.ItemCallback<NotesListItemBaseViewModel>() {
            override fun areItemsTheSame(
                oldItem: NotesListItemBaseViewModel,
                newItem: NotesListItemBaseViewModel
            ): Boolean =
                if (oldItem is NotesListItemViewModel && newItem is NotesListItemViewModel) {
                    oldItem.id == newItem.id
                } else if (oldItem is NotesListItemDateViewModel && newItem is NotesListItemDateViewModel) {
                    oldItem.date == newItem.date
                } else {
                    false
                }


            override fun areContentsTheSame(
                oldItem: NotesListItemBaseViewModel,
                newItem: NotesListItemBaseViewModel
            ): Boolean =
                if (oldItem is NotesListItemViewModel && newItem is NotesListItemViewModel) {
                    oldItem.title == newItem.title
                } else if (oldItem is NotesListItemDateViewModel && newItem is NotesListItemDateViewModel) {
                    oldItem.date == newItem.date
                } else {
                    false
                }

        })

    private val notesListItemClickListener = OnNotesListItemClickListener { item ->
        reduce(NotesListPartialState.OnItemClick(item.id))
    }

    val itemBinding: OnItemBindClass<NotesListItemBaseViewModel> =
        OnItemBindClass<NotesListItemBaseViewModel>()
            .map(
                NotesListItemDateViewModel::class.java,
                BR.viewModel,
                R.layout.view_notes_list_item_date
            )
            .map(NotesListItemViewModel::class.java) { itemBinding, _, _ ->
                itemBinding.clearExtras().set(BR.viewModel, R.layout.view_noteslist_item)
                    .bindExtra(BR.listener, notesListItemClickListener)
            }

    fun initialize() {
        reduce(NotesListPartialState.Init)
        getAllNotes()
    }

    fun getAllNotes() {
        viewModelScope.launch {
            try {
                showLoading()
                getAllNotesUseCase.run().collect { notesList ->
                    val itemsList = notesListItemFactory.assemble(notesList)
                    hideLoading()
                    this@NotesListViewModel.itemsList.update(itemsList)
                }
            } catch (e: Exception) {
                hideLoading()
                Timber.e(e)
            }
        }
    }

    fun onAddNewNoteClick() {
        reduce(NotesListPartialState.OnAddNewNoteClick)
    }

    private fun showLoading() {
        reduce(NotesListPartialState.Loading(isLoading = true))
    }

    private fun hideLoading() {
        reduce(NotesListPartialState.Loading(isLoading = false))
    }

    private fun reduce(partialState: NotesListPartialState) {
        val currentState = _state.value ?: NotesListState.initialize()
        when (partialState) {
            is NotesListPartialState.Init -> {
                _state.value = NotesListState.initialize()
            }
            is NotesListPartialState.Loading -> {
                _state.value =
                    currentState.copy(isLoading = partialState.isLoading, false, null, false)
            }
            is NotesListPartialState.OnItemClick -> {
                _state.value =
                    currentState.copy(isItemClicked = true, itemClickedId = partialState.itemId)
            }
            is NotesListPartialState.OnAddNewNoteClick -> {
                _state.value = NotesListState.of(
                    false, false, null, isAddNewNoteClicked = true
                )
            }
        }
    }
}