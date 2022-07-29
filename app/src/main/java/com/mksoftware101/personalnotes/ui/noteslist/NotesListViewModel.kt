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
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.OnItemBind
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel
@Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val notesListItemFactory: NotesListItemFactory
) : ViewModel() {

    init {
        getAllNotes()
    }

    private var _state = MutableLiveData<NotesListState>()
    val state: LiveData<NotesListState> = _state

    val listener = OnNotesListItemClickListener { item ->
        val onItemClickedPartialState = NotesListPartialState.OnItemClicked(item.id)
        reduce(onItemClickedPartialState)
    }

    val items = DiffObservableList(object : DiffUtil.ItemCallback<NotesListItemBaseViewModel>() {
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

    val itemBinding: OnItemBindClass<NotesListItemBaseViewModel> =
        OnItemBindClass<NotesListItemBaseViewModel>()
            .map(
                NotesListItemDateViewModel::class.java,
                BR.viewModel,
                R.layout.view_notes_list_item_date
            )
            .map(NotesListItemViewModel::class.java, object : OnItemBind<NotesListItemViewModel> {
                override fun onItemBind(
                    itemBinding: ItemBinding<*>,
                    position: Int,
                    item: NotesListItemViewModel?
                ) {
                    itemBinding.clearExtras().set(BR.viewModel, R.layout.view_noteslist_item)
                        .bindExtra(BR.listener, listener)
                }

            })

    fun initialize() {
        reduce(NotesListPartialState.Init)
    }

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.run().collect { notesList ->
                val itemsList = notesListItemFactory.assemble(notesList)
                items.update(itemsList)
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