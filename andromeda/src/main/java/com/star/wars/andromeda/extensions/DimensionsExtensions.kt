package com.star.wars.andromeda.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

internal fun Float.toPxFloat(context: Context): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
            context.resources.displayMetrics)
}

internal fun Float.toPxInt(context: Context): Int {
    return toPxFloat(context).roundToInt()
}

fun Float.dpToPx(): Float =
    this * Resources.getSystem().displayMetrics.density

fun Float.pxToDp(): Float =
    this / Resources.getSystem().displayMetrics.density

internal fun Int.toPxFloat(context: Context): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            context.resources.displayMetrics)
}

internal fun Resources.getScreenWidth(): Int = displayMetrics.widthPixels

internal fun Resources.getScreenHeight(): Int = displayMetrics.heightPixels

internal fun Float.getLineHeightDp(
        context: Context) = div(
        context.resources.displayMetrics.scaledDensity
)
