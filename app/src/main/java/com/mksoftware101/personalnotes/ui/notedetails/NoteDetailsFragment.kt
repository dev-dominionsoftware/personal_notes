package com.mksoftware101.personalnotes.ui.notedetails

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.databinding.FragmentNoteDetailsBinding
import com.mksoftware101.personalnotes.ui.common.NotesListConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailsFragment : Fragment() {

    val args: NoteDetailsFragmentArgs by navArgs()

    private var binding: FragmentNoteDetailsBinding? = null
    private val viewModel by viewModels<NoteDetailsViewModel>()
    private var currentNavigationIcon: Drawable? = null
    private var isNoteChanged = false

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
        viewModel.initialize()
    }

    private fun render(state: NoteDetailsState) {
        if (state.isOperationDone == true) {
            backToHome()
        }

        state.isCreateNoteSuccessfully?.let { isCreateNoteSuccessfully ->
            if (isCreateNoteSuccessfully) {
                backToHome()
            } else {
                showSnackbar(R.string.noteDetailsCreateNoteFailed)
            }
        }

        handleNoteFetched(state.isNoteFetched)
        changeNavigationIconIfNeeded(state.isNoteChanged)
    }

    private fun handleNoteFetched(isNoteFetched: Boolean?) {
        if (isNoteFetched == false) {
            showSnackbar(R.string.noteDetailsNoteFetchedFailed)
            disableAll()
        }
    }

    private fun disableAll() {
        binding?.let {
            it.noteTitleEditText.isEnabled = false
            it.noteDataEditText.isEnabled = false
        }
    }

    private fun showSnackbar(@StringRes textId: Int) {
        binding?.let {
            Snackbar.make(it.detailsCoordinatorLayout, getText(textId), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupNavigationHome() {
        setNavigationIcon(getDrawableBy(false))
        binding?.noteDetailsTopAppBar?.setNavigationOnClickListener {
            if (isNoteChanged) {
                viewModel.saveNote()
            } else {
                backToHome()
            }
        }
    }

    private fun setupTitle() {
        if (args.itemId == NotesListConstants.NOTE_ID_UNDEFINED) {
            binding?.noteDetailsTopAppBar?.setTitle(R.string.noteDetailsTitleCreate)
        } else {
            binding?.noteDetailsTopAppBar?.setTitle(R.string.noteDetailsTitleEdit)
        }
    }

    private fun changeNavigationIconIfNeeded(isNoteChanged: Boolean) {
        this@NoteDetailsFragment.isNoteChanged = isNoteChanged
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
            R.drawable.ic_baseline_close_white_24
        }
    )

    private fun setNavigationIcon(icon: Drawable?) {
        icon?.let { iconDrawable ->
            binding?.noteDetailsTopAppBar?.navigationIcon = iconDrawable
            currentNavigationIcon = iconDrawable
        }
    }

    private fun backToHome() {
        findNavController().popBackStack()
    }
}