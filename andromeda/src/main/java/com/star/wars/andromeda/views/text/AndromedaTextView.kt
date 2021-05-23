package com.star.wars.andromeda.views.text

import android.content.Context
import android.util.AttributeSet
import android.graphics.Color as AndroidColor
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import com.star.wars.andromeda.Color
import com.star.wars.andromeda.R

/**
 * Andromeda TextView Component
 *
 * XML Usage:
 * ```xml
 * <com.star.wars.andromeda.views.text.AndromedaTextView
 *  android:layout_width="wrap_content"
 *  android:layout_height="wrap_content"
 *  android:text="Some text"
 *  app:typographyStyle="title_hero_default" />
 * ```
 *
 * You can set the Typography Style to the AndromedaTextView by setting the value of [typographyStyle]
 * or the xml attribute `app:typographyStyle`. By default the value of [typographyStyle] is
 * [TypographyStyle.TITLE_HERO_DEFAULT]
 *
 */

open class AndromedaTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    var typographyStyle: TypographyStyle = TypographyStyle.TITLE_HERO_DEFAULT
        set(value) {
            field = value
            setStyle(value.style, true)
        }

    private var spacingAdd = 0f

    init {
        attrs?.let { attributeSet ->
            val typedArray = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.AndromedaTextView, 0, 0
            )
            val styleOrdinal = typedArray.getInt(R.styleable.AndromedaTextView_typographyStyle, 0)
            typographyStyle = TypographyStyle.values()[styleOrdinal]
            typedArray.recycle()
        }
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        spacingAdd = add
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        //on Kitkat and below lineSpacingExtra have effect on single line text as well.
        // Setting lineSpacingExtra will mess up the alignment in pre-lollipop devices for single line text
        if (lineCount < 2) {
            super.setLineSpacing(0f, 1f)
        } else {
            //If the lineSpacingExtra was same before text change, don't trigger another redraw by setting same lineSpacingExtra
            if (lineSpacingExtra != spacingAdd) {
                super.setLineSpacing(spacingAdd, 1f)
            }
        }
    }

    fun setTextColor(color: Color) {
        when(color) {
            Color.DEFAULT,
            Color.PRIMARY -> {
                TextViewCompat.setTextAppearance(this, typographyStyle.style)
            }
            else -> super.setTextColor(AndroidColor.parseColor(color.value))
        }
    }
}
