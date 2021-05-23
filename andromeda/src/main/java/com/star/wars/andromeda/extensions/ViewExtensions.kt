package com.star.wars.andromeda.extensions

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.star.wars.andromeda.Color
import com.star.wars.andromeda.R
import com.star.wars.andromeda.views.text.TypographyStyle
import android.graphics.Color as AndroidColor

internal inline fun <reified T : Drawable> View.getBackgroundDrawable() = background as T

internal fun AppCompatImageView.setImageUrl(url:String) {
    load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_default_image)
        listener(onError = {_,throwable ->
            throwable.printStackTrace()
        })
    }
}

internal inline fun View.readAttributes(
    attrs: AttributeSet?,
    styleableArray: IntArray,
    block: (TypedArray) -> Unit
) {
    val typedArray = context.theme.obtainStyledAttributes(attrs, styleableArray, 0, 0)
    typedArray.use(block)
}

internal fun View.isInTouchArea(event: MotionEvent): Boolean {
    return event.x.toInt() in left..right &&
            event.y.toInt() in top..bottom
}

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}

class AndromedaTypographySpan(context: Context, typographyStyle: TypographyStyle) :
    TextAppearanceSpan(context, typographyStyle.style)

fun spannable(func: () -> SpannableString) = func()
private fun span(s: CharSequence, o: Any) =
    (if (s is String) SpannableString(s) else s as? SpannableString
        ?: SpannableString(""))
        .apply { setSpan(o, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) }

operator fun SpannableString.plus(s: SpannableString) =
    SpannableString(TextUtils.concat(this, s))

operator fun SpannableString.plus(s: String) =
    SpannableString(TextUtils.concat(this, s))

fun text(s: CharSequence) =
    span(s, StyleSpan(Typeface.NORMAL))

fun bold(s: CharSequence) =
    span(s, StyleSpan(Typeface.BOLD))

fun italic(s: CharSequence) =
    span(s, StyleSpan(Typeface.ITALIC))

fun sub(s: CharSequence) =
    span(s, SubscriptSpan()) // baseline is lowered

fun size(size: Float, s: CharSequence) =
    span(s, RelativeSizeSpan(size))

fun underline(s: CharSequence) =
    span(s, UnderlineSpan())

fun typography(s: CharSequence, context: Context, typography: TypographyStyle) =
    span(s, AndromedaTypographySpan(context, typography))

fun background(color: Color, s: CharSequence) =
    span(s, BackgroundColorSpan(AndroidColor.parseColor(color.value)))

fun color(color: Int, s: CharSequence) =
    span(s, ForegroundColorSpan(color))

fun color(color: Color, s: CharSequence) =
    span(s, ForegroundColorSpan(AndroidColor.parseColor(color.value)))

fun url(url: String, s: CharSequence) =
    span(s, URLSpan(url))

// For Resourses
fun Int.dpToPixels() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Float.dpToPixels() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.getAllChildren(): ArrayList<View> {
    if (this !is ViewGroup) {
        val viewArrayList = ArrayList<View>()
        viewArrayList.add(this)
        return viewArrayList
    }
    val result = ArrayList<View>()
    val viewGroup: ViewGroup = this
    for (i in 0 until viewGroup.childCount) {
        val child: View = viewGroup.getChildAt(i)
        val viewArrayList = ArrayList<View>()
        viewArrayList.add(this)
        viewArrayList.addAll(child.getAllChildren())
        result.addAll(viewArrayList)
    }
    return result
}

fun View.clickable() {
    isClickable = true
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
        this.foreground = ContextCompat.getDrawable(context, outValue.resourceId)
    }
}

fun View.unclickable() {
    isClickable = false
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        this.foreground = null
    }
}

fun View.clickableWithCircleRipple() {
    isClickable = true
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
        this.foreground = ContextCompat.getDrawable(context, outValue.resourceId)
    }
}
