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
import com.mksoftware101.personalnotes.ui.noteslist.NotesListConstants.NOTES_LIST_NO_ITEM_ID
import com.mksoftware101.personalnotes.ui.noteslist.states.NotesListState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment : Fragment() {

    private val viewModel by viewModels<NotesListViewModel>()
    private var _binding: FragmentNoteslistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_noteslist, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.viewModel = viewModel
        viewModel.state.observe(viewLifecycleOwner) { state -> render(state) }
        viewModel.initialize()
        binding.notesSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllNotes()
        }
    }

    private fun render(state: NotesListState) {
        binding.notesSwipeRefreshLayout.isRefreshing = state.isLoading

        if (state.isItemClicked) {
            openDetailsScreen(state.itemClickedId)
        }
        if (state.isAddNewNoteClicked) {
            openDetailsScreen(itemId = null)
        }
    }

    private fun openDetailsScreen(itemId: Long?) {
        val navigateAction =
            NotesListFragmentDirections.actionNotesListFragmentToNoteDetailsFragment(
                itemId ?: NOTES_LIST_NO_ITEM_ID
            )
        findNavController().navigate(navigateAction)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}