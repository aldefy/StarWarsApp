package com.star.wars.andromeda.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

internal fun Context.getColorCompat(@ColorRes id: Int) = ContextCompat.getColor(this, id)
internal fun Context.getDrawableCompat(@DrawableRes id: Int) = AppCompatResources.getDrawable(this, id)
