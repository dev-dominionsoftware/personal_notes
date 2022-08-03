package com.mksoftware101.personalnotes.ui.noteslist.item.thumbnail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.mksoftware101.personalnotes.R
import com.mksoftware101.personalnotes.databinding.ViewLetterInCircleBinding

class LetterInCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val layout: ViewLetterInCircleBinding by lazy {
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_letter_in_circle,
            this,
            true
        )
    }

    private val lettersToColorMap = mapOf(
        'A'..'D' to R.color.color1,
        'E'..'H' to R.color.color2,
        'I'..'L' to R.color.color3,
        'M'..'P' to R.color.color4,
        'Q'..'T' to R.color.color5,
        'U'..'X' to R.color.color6,
        'Y'..'Z' to R.color.color7,
    )

    init {
        val circleLetterAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.LetterInCircle)
        val letterText = circleLetterAttributes.getString(R.styleable.LetterInCircle_letter) ?: ""
        setLetter(letterText)
        circleLetterAttributes.recycle()
    }

    /**
     * Set upper case letter to the center of a view.
     *
     * @param letterText String contains letter or some string from which first letter will be extracted.
     * */
    fun setLetter(letterText: String) {
        var letterColorId: Int = R.color.color_default
        if (letterText.isNotBlank() && letterText[0].isLetter()) {
            val letter: Char = letterText[0].toUpperCase()
            letterColorId = lettersToColorMap.filter { it.key.contains(letter) }.map { it.value }[0]
            layout.letterTextView.text = letter.toString()
        }
        layout.circleLetterLayout.backgroundTintList =
            context.resources.getColorStateList(letterColorId, context.theme)
    }
}