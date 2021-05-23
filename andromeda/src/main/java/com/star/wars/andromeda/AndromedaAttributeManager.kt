package com.star.wars.andromeda

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

/**
 * AndromedaAttributeManager
 *
 * [getColorFromAttribute] can be used to get the int value of the color given an attribute. The
 * The value returned will be according to the theme which is currently set.
 *
 * [getDimensionFromAttribute] can be used to get the int value of the dimension given an attribute. The
 * The value returned will be according to the theme which is currently set.
 */

object AndromedaAttributeManager {

    fun getColorFromAttribute(context: Context, @AttrRes attributeResId: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attributeResId, typedValue, true)
        return typedValue.data
    }

    fun getDimensionFromAttribute(context: Context, @AttrRes attributeResId: Int): Float {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attributeResId, typedValue, true)
        return typedValue.getDimension(context.resources.displayMetrics)
    }
}
