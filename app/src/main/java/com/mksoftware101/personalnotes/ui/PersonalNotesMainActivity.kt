package com.mksoftware101.personalnotes.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mksoftware101.personalnotes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalNotesMainActivity : AppCompatActivity() {

    val viewModel by viewModels<PersonalNotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllNotes()
    }
}