package com.star.wars.andromeda.views.divider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.getSize
import com.star.wars.andromeda.R
import com.star.wars.andromeda.views.divider.AndromedaDivider.DividerType
import com.star.wars.andromeda.views.divider.internal.BigDivider
import com.star.wars.andromeda.views.divider.internal.Divider
import com.star.wars.andromeda.views.divider.internal.DottedDivider
import com.star.wars.andromeda.views.divider.internal.PlainDivider

/**
 * Andromeda Divider Component
 *
 * XML Usage:
 * ```xml
 * <com.star.wars.andromeda.views.divider.AndromedaDivider
 *  android:layout_width="match_parent"
 *  android:layout_height="wrap_content"
 *  app:divider_type="dotted" />
 * ```
 *
 * Kotlin Usage:
 * ```kotlin
 * val dividerDotted = AndromedaDivider(context = this)
 * dividerDotted.setType(AndromedaDivider.DOTTED)
 * ```
 *
 * AndromedaDivider has two types: [DividerType.PLAIN] and [DividerType.DOTTED]
 * By default the type is set to [DividerType.PLAIN]
 * You can set the type of divider using [setType] method or setting the xml
 * attribute `app:divider_type`
 *
 */
class AndromedaDivider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    /**
     * Defines type of AndromedaDivider
     */
    enum class DividerType {
        PLAIN,
        DOTTED,
        BIG
    }

    private val path = Path()

    private var divider: Divider = getDivider(DividerType.PLAIN)

    init {
        attrs?.let { attributeSet ->
            val typedArray = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.AndromedaDivider,
                0,
                0
            )
            val typeOrdinal = typedArray.getInt(R.styleable.AndromedaDivider_divider_type, 0)
            setType(DividerType.values()[typeOrdinal])
            typedArray.recycle()
        }
    }

    /**
     * @suppress
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getSize(widthMeasureSpec)
        val height = divider.height
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (path.isEmpty) {
            generatePath()
        }
        divider.backgroundPaint?.let { canvas.drawPaint(it) }
        canvas.drawPath(path, divider.paint)
    }

    fun setType(type: DividerType) {
        divider = getDivider(type)
        path.reset()
        invalidate()
    }

    private fun generatePath() {
        val dividerHeight = divider.dividerLineHeight
        path.apply {
            moveTo(0f, dividerHeight / 2f)
            lineTo(measuredWidth.toFloat(), dividerHeight / 2f)
        }
    }

    private fun getDivider(type: DividerType): Divider {
        return when (type) {
            DividerType.PLAIN -> PlainDivider(context)
            DividerType.DOTTED -> DottedDivider(context)
            DividerType.BIG -> BigDivider(context)
        }
    }
}
