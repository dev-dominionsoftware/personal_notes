package com.mksoftware101.personalnotes.ui.noteslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.mksoftware101.personalnotes.BR
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.domain.GetAllNotesUseCase
import com.mksoftware101.personalnotes.ui.noteslist.item.NotesListItemViewModel
import com.mksoftware101.personalnotes.ui.noteslist.item.OnNotesListItemClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel
@Inject constructor(val getAllNotesUseCase: GetAllNotesUseCase) : ViewModel() {

    init {
        getAllNotes()
    }

    val listener = OnNotesListItemClickListener { item ->
        Timber.d("[d] clicked id=${item.title}")
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

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.run().collect { notesList ->
                val newItems = notesList.map { NotesListItemViewModel(it.Id, it.title) }
                items.update(newItems)
            }
        }
    }
}