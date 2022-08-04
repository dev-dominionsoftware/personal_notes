package com.mksoftware101.personalnotes.ui.noteslist.item.thumbnail

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("letter")
fun setLetter(view: View, letter: String) {
    val letterInCircleView = view as LetterInCircle
    letterInCircleView.setLetter(letter)
}