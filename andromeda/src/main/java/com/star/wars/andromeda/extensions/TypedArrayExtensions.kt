package com.star.wars.andromeda.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.annotation.StyleableRes

internal fun <T : Enum<*>> TypedArray.getEnum(index: Int, enumValues: Array<T>, default: T): T {
    val ordinal = getInteger(index, -1)
    return if (ordinal == -1) {
        default
    } else {
        enumValues[ordinal]
    }
}

internal fun TypedArray.getDrawableCompat(context: Context, @StyleableRes id: Int): Drawable? {
    val resource = getResourceId(id, 0)
    if (resource != 0) {
        return context.getDrawableCompat(resource)
    }
    return null
}

internal fun TypedArray.getStringViaResources(@StyleableRes id: Int): String? {
    val textResourceId = getResourceId(id, 0)
    return if (textResourceId != 0) {
        resources.getString(textResourceId)
    } else {
        getString(id)
    }
}
