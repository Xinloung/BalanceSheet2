package com.continentalbakingco.balancesheet20.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

class MTextView  (context: Context, attrs: AttributeSet): AppCompatTextView(context, attrs) {
    init {
        applyFont()
    }
    private fun applyFont() {
        val typeface : Typeface = Typeface.createFromAsset(context.assets,"Montserrat-SemiBold.ttf")
        setTypeface(typeface)
    }

}