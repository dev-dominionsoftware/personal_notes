package com.mksoftware101.personalnotes.ui.notedetails

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        viewModel.state.observe(viewLifecycleOwner) { state -> render(state) }
    }

    private var currentNavigationIcon: Drawable? = null

    private fun render(state: NotesDetailsState) {
        changeNavigationIconIfNeeded(state.isNoteChanged)
    }

//    private fun startListenForTyping() {
//        binding?.noteTitleEditText?.doOnTextChanged { _, _, _, _ ->
//            changeNavigationIcon()
//        }
//        binding?.noteDataEditText?.doOnTextChanged { _, _, _, _ ->
//            changeNavigationIcon()
//        }
//    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupNavigationHome() {
        setNavigationIcon(getDrawableBy(false))
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

    private fun changeNavigationIconIfNeeded(isNoteChanged: Boolean) {
        val candidateNavigationIcon = getDrawableBy(isNoteChanged)
        if (currentNavigationIcon != candidateNavigationIcon) {
            setNavigationIcon(candidateNavigationIcon)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getDrawableBy(isNoteChanged: Boolean) = requireContext().getDrawable(
        if (isNoteChanged) {
            R.drawable.ic_baseline_done_white_24
        } else {
            R.drawable.ic_baseline_arrow_back_white_24
        }
    )

    private fun setNavigationIcon(icon: Drawable?) {
        icon?.let { iconDrawable ->
            binding?.noteDetailsTopAppBar?.navigationIcon = iconDrawable
            currentNavigationIcon = iconDrawable
        }
    }
}