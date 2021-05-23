package com.star.wars.andromeda.views.text

import android.view.View
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import com.star.wars.andromeda.extensions.DebounceClickListener

fun TextView.setStyle(
        @StyleRes style: Int,
        useLineSpacing: Boolean = false
) {
    TextViewCompat.setTextAppearance(this, style)
    // Duplicate of TextViewCompat.setLineHeight but with float values to be more accurate
    if (useLineSpacing) {
        val attrs = intArrayOf(android.R.attr.lineSpacingExtra)
        val typedArray = context.theme.obtainStyledAttributes(style, attrs)
        val styleLineHeight = typedArray.getDimension(0, lineSpacingExtra)
        setLineSpacing(styleLineHeight - textSize, 1f)
        typedArray.recycle()
    }
}

internal inline fun TextView.onDebounceClickListener(crossinline listener: () -> Unit) {
    setOnClickListener(object : DebounceClickListener() {
        override fun onClick(view: View) {
            listener.invoke()
        }
    })
}
