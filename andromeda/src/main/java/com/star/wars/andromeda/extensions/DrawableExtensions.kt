package com.star.wars.andromeda.extensions

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat

internal fun Drawable.tint(colorInt: Int): Drawable {
    return DrawableCompat.wrap(this).apply {
        DrawableCompat.setTint(this, colorInt)
    }
}
