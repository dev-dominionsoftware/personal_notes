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

    private val args: NoteDetailsFragmentArgs by navArgs()
    private val isCreateNewNote get() = args.itemId == NotesListConstants.NOTE_ID_UNDEFINED

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NoteDetailsViewModel>()

    private var currentNavigationIcon: Drawable? = null
    private var isNoteChanged = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() {
        binding.viewModel = viewModel
        with(viewModel) {
            state.observe(viewLifecycleOwner) { state -> render(state) }
            initialize()
            getNoteBy(args.itemId)
        }
        setupTitle()
        setupNavigationHome()
        setupToolbarMenu()
    }

    private fun render(state: NoteDetailsState) {
        state.isNoteFetched?.let { isSuccess ->
            if (!isSuccess) {
                showSnackbar(R.string.noteDetailsNoteFetchedFailed)
                disableAll()
            }
        }

        state.isCreateNoteSuccessfully?.let { isSuccess ->
            handleCRUDoperation(isSuccess, R.string.noteDetailsCreateNoteFailed)
        }

        state.isEditNoteSuccessfully?.let { isSuccess ->
            handleCRUDoperation(isSuccess, R.string.noteDetailsEditNoteFailed)
        }

        state.isDeleteNoteSuccessfully?.let { isSuccess ->
            handleCRUDoperation(isSuccess, R.string.noteDetailsDeleteNoteFailed)
        }

        changeNavigationIconIfNeeded(state.isNoteChanged)
    }

    private fun handleCRUDoperation(isSuccess: Boolean, @StringRes stringResId: Int) {
        if (isSuccess) {
            backToHome()
        } else {
            showSnackbar(stringResId)
        }
    }

    private fun setupToolbarMenu() {
        if (isCreateNewNote) {
            hideDeleteIcon()
        }

        binding.noteDetailsToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.noteDetailsMenuDeleteNote -> {
                    viewModel.deleteNote()
                    true
                }
                else -> false
            }
        }
    }

    private fun hideDeleteIcon() {
        binding.noteDetailsToolbar.menu.getItem(0).isVisible = false
    }

    private fun disableAll() {
        binding.let {
            it.noteTitleEditText.isEnabled = false
            it.noteDataEditText.isEnabled = false
        }
    }

    private fun showSnackbar(@StringRes textId: Int) {
        binding.let {
            Snackbar.make(it.detailsCoordinatorLayout, getText(textId), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupNavigationHome() {
        setNavigationIcon(getDrawableBy(false))
        binding.noteDetailsToolbar.setNavigationOnClickListener {
            if (isNoteChanged) {
                viewModel.saveNote()
            } else {
                backToHome()
            }
        }
    }

    private fun setupTitle() {
        binding.noteDetailsToolbar.setTitle(
            if (isCreateNewNote) {
                R.string.noteDetailsTitleCreate
            } else {
                R.string.noteDetailsTitleEdit
            }
        )
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
            binding.noteDetailsToolbar.navigationIcon = iconDrawable
            currentNavigationIcon = iconDrawable
        }
    }

    private fun backToHome() {
        findNavController().popBackStack()
    }
}