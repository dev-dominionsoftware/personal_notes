package com.mksoftware101.personalnotes.ui.notedetails

import android.os.Bundle
import android.view.ContextMenu
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
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NoteDetailsFragment : Fragment() {

    val args: NoteDetailsFragmentArgs by navArgs()

    private var binding: FragmentNoteDetailsBinding? = null
    private val viewModel by viewModels<NoteDetailsViewModel>()

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
        if (args.itemId == -1L) {
            binding?.noteDetailsTopAppBar?.setTitle(R.string.noteDetailsTitleCreate)
        } else {
            binding?.noteDetailsTopAppBar?.setTitle(R.string.noteDetailsTitleEdit)
        }

        binding?.noteDetailsTopAppBar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding?.noteDetailsTopAppBar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.noteDetailsMenuDeleteNote -> {
                    viewModel.deleteNote()
                    true
                }
                else -> false
            }
        }
    }
}