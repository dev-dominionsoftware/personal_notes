package com.mksoftware101.personalnotes.ui.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.databinding.FragmentNoteslistBinding
import com.mksoftware101.personalnotes.ui.noteslist.states.NotesListState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NotesListFragment : Fragment() {

    val viewModel by viewModels<NotesListViewModel>()
    var binding: FragmentNoteslistBinding? = null

    companion object {
        const val logTag = "NotesListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_noteslist, container, false)
        binding?.viewModel = viewModel
        viewModel.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        viewModel.initialize()
        binding?.notesSwipeRefreshLayout?.setOnRefreshListener {
            binding?.notesSwipeRefreshLayout?.isRefreshing = false
            viewModel.getAllNotes()
        }
        return binding?.root
    }

    private fun render(state: NotesListState) {
        if (state.isItemClicked) {
            openDetailsScreen(state.itemClickedId)
        }
        if (state.isAddNewNoteClicked) {
            openDetailsScreen(itemId = null)
        }
    }

    private fun openDetailsScreen(itemId: Long?) {
        val navigateAction =
            NotesListFragmentDirections.actionNotesListFragmentToNoteDetailsFragment(itemId ?: -1)
        findNavController().navigate(navigateAction)
    }
}