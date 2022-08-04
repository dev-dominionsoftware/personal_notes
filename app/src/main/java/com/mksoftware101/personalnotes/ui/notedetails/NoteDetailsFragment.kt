package com.mksoftware101.personalnotes.ui.notedetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.databinding.FragmentNoteDetailsBinding
import com.mksoftware101.personalnotes.ui.noteslist.NotesListConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailsFragment : Fragment() {

    val args: NoteDetailsFragmentArgs by navArgs()

    private var binding: FragmentNoteDetailsBinding? = null
    private val viewModel by viewModels<NoteDetailsViewModel>()
    private var isNavigationIconChanged = false

    companion object {
        const val logTag = "NoteDetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false)
        binding?.viewModel = viewModel
        viewModel.getNoteBy(args.itemId)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupNavigationHome()

        binding?.noteDetailsTopAppBar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.noteDetailsMenuDeleteNote -> {
                    viewModel.deleteNote()
                    true
                }
                else -> false
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state == NoteDetailsState.OperationDoneSuccessfully) {
                findNavController().popBackStack()
            }
        }
    }

    private fun startListenForTyping() {
        binding?.noteTitleEditText?.doOnTextChanged { _, _, _, _ ->
            changeNavigationIcon()
        }
        binding?.noteDataEditText?.doOnTextChanged { _, _, _, _ ->
            changeNavigationIcon()
        }
    }


    override fun onStart() {
        super.onStart()
        startListenForTyping()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupNavigationHome() {
        binding?.noteDetailsTopAppBar?.navigationIcon =
            requireContext().getDrawable(R.drawable.ic_baseline_arrow_back_white_24)
        binding?.noteDetailsTopAppBar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTitle() {
        if (args.itemId == NotesListConstants.NOTES_LIST_NO_ITEM_ID) {
            binding?.noteDetailsTopAppBar?.setTitle(R.string.noteDetailsTitleCreate)
        } else {
            binding?.noteDetailsTopAppBar?.setTitle(R.string.noteDetailsTitleEdit)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeNavigationIcon() {
        if (!isNavigationIconChanged) {
            binding?.noteDetailsTopAppBar?.navigationIcon =
                requireContext().getDrawable(R.drawable.ic_baseline_done_white_24)
            isNavigationIconChanged = true
        }
    }
}