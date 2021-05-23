package com.star.wars.andromeda.views.shadow

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.graphics.ColorUtils
import com.star.wars.andromeda.AndromedaAttributeManager
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.getEnum
import com.star.wars.andromeda.extensions.readAttributes
import com.star.wars.andromeda.extensions.toPxFloat


/**
 * Andromeda ShadowLayout
 *
 * A simple FrameLayout based ViewGroup to add shadow to any view
 * Even though this layout can host more than one child, shadow will be added only to the first child
 * If the child has a rounded corner background/shape, shadow shape can also be made same by mentioning the [cornerRadius] property
 * Certain views might not need shadow in all sides. For example a bottom navigation bar which is of match_parent width.
 * Views with height or width set to [android.view.ViewGroup.LayoutParams.MATCH_PARENT] are handled internally. There won't be shadow added to the sides with match_parent dimension
 * But if dimension is not set to match_parent and still want to disable shadow in certain side, `enable_shadow_left/right/top/bottom` property in xml or [setShadowRegion] method can be used
 *
 * When child's height and width are set to match_parent or set to a fixed dimension which is same as AndromedaShadowLayout's layout cannot be shows due to the lack of space
 * But if shadow has to be displayed in that case set the <pre>force_shadow</pre> property to true in layout xml
 *
 * ```xml
 * <com.star.wars.andromeda.views.shadow.AndromedaShadowLayout
 *      android:layout_width="match_parent"
 *      android:layout_height="wrap_content"
 *      app:sl_type="high"
 *      app:corner_radius="56dp">
 *
 *      <Your View..../>
 *
 * </com.star.wars.andromeda.views.shadow.AndromedaShadowLayout>
 * ```
 *
 */
class AndromedaShadowLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(
    context,
    attributeSet
) {
    private var defaultShadowColorAlpha: Int = 254
    private var shadowColor = 0
    private var shadowRadius = 0f
    private var cornerRadius = 0F
    private var dx = 0f
    private var dy = 0f
    private var shadowRegion = ShadowRegion()
    private var force = false
    private var animator: Animator? = null
    private var originalShadowRadius = 0f

    init {
        var shadowType = ShadowType.LOW
        readAttributes(attributeSet, R.styleable.AndromedaShadowLayout) {
            cornerRadius = it.getDimension(R.styleable.AndromedaShadowLayout_corner_radius, 0F)
            shadowType = it.getEnum(R.styleable.AndromedaShadowLayout_sl_type, ShadowType.values(), shadowType)
            shadowRegion = shadowRegion.copy(
                left = it.getBoolean(R.styleable.AndromedaShadowLayout_enable_shadow_left, shadowRegion.left),
                top = it.getBoolean(R.styleable.AndromedaShadowLayout_enable_shadow_top, shadowRegion.top),
                right = it.getBoolean(R.styleable.AndromedaShadowLayout_enable_shadow_right, shadowRegion.right),
                bottom = it.getBoolean(R.styleable.AndromedaShadowLayout_enable_shadow_bottom, shadowRegion.bottom)
            )
            force = it.getBoolean(R.styleable.AndromedaShadowLayout_force_shadow, force)
            updateShadowParameters(shadowType)
            val shadowEnabled = it.getBoolean(R.styleable.AndromedaShadowLayout_shadow_enabled, true)
            if(!shadowEnabled) {
                shadowRadius = 0f
            }
        }
        adjustAlpha()
    }

    fun showShadow() {
        animator = showShadowAnimator().also { it.start() }
    }

    fun hideShadow() {
        animator = hideShadowAnimator().also { it.start() }
    }

    fun disableShadow() {
        shadowRadius = 0f
        requestLayout()
    }

    /**
     * Shows the shadow
     */
    fun enableShadow() {
        shadowRadius = originalShadowRadius
        requestLayout()
    }


    /**
     * It is highly recommended to not use this method. It triggers a layout pass of entire view hierarchy.
     * Use it only if the region to disable shadow can be identified at runtime. Otherwise use xml attributes
     */
    fun setShadowRegion(
        left: Boolean = true,
        top: Boolean = true,
        right: Boolean = true,
        bottom: Boolean = true
    ) {
        shadowRegion = ShadowRegion(
            left,
            top,
            right,
            bottom
        )

        requestLayout()
    }

    fun setShadow(shadowType: ShadowType = ShadowType.HIGH, cornerRadius: Float) {
        this.cornerRadius = cornerRadius.toPxFloat(context)
        updateShadowParameters(shadowType)
        requestLayout()
    }

    /** @suppress **/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val shadowRadiusInt = shadowRadius.toInt()

        if (force) {
            val leftPadding = if (shadowRegion.left) shadowRadiusInt else paddingLeft
            val topPadding = if (shadowRegion.top) shadowRadiusInt else paddingTop
            val rightPadding = if (shadowRegion.right) shadowRadiusInt else paddingRight
            val bottomPadding = if (shadowRegion.bottom) shadowRadiusInt else paddingBottom
            setPadding(
                leftPadding,
                topPadding,
                rightPadding,
                bottomPadding
            )
        }

        val child = getChildAt(0)

        measureChild(
            child,
            widthMeasureSpec,
            heightMeasureSpec
        )

        val width = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            var computedWidth = child.measuredWidth
            if (shadowRegion.left) {
                computedWidth += shadowRadiusInt
            }
            if (shadowRegion.right) {
                computedWidth += shadowRadiusInt
            }
            computedWidth
        }

        val height = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            var computedHeight = child.measuredHeight
            if (shadowRegion.top) {
                computedHeight += shadowRadiusInt
            }
            if (shadowRegion.bottom) {
                computedHeight += shadowRadiusInt
            }
            computedHeight
        }

        setMeasuredDimension(width, height)
    }

    /** @suppress **/
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val childRect = getChildRect()
        getChildAt(0).layout(
            childRect.left,
            childRect.top,
            childRect.right,
            childRect.bottom
        )
    }

    /** @suppress **/
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 &&
            h > 0) {
            updateBackground()
        }
    }

    /** @suppress **/
    override fun onDetachedFromWindow() {
        if (animator?.isRunning == true) {
            animator?.cancel()
        }
        super.onDetachedFromWindow()
    }

    private fun updateShadowParameters(shadowType: ShadowType) {
        when (shadowType) {
            ShadowType.LOW -> {
                originalShadowRadius = AndromedaAttributeManager.getDimensionFromAttribute(context, R.attr.shadow_low_blur)
                dx = AndromedaAttributeManager.getDimensionFromAttribute(context, R.attr.shadow_low_x)
                dy = AndromedaAttributeManager.getDimensionFromAttribute(context, R.attr.shadow_low_y)
                shadowColor = AndromedaAttributeManager.getColorFromAttribute(context, R.attr.shadow_low_color)
            }
            ShadowType.HIGH -> {
                originalShadowRadius = AndromedaAttributeManager.getDimensionFromAttribute(context, R.attr.shadow_high_blur)
                dx = AndromedaAttributeManager.getDimensionFromAttribute(context, R.attr.shadow_high_x)
                dy = AndromedaAttributeManager.getDimensionFromAttribute(context, R.attr.shadow_high_y)
                shadowColor = AndromedaAttributeManager.getColorFromAttribute(context, R.attr.shadow_high_color)
            }
        }
        shadowRadius = originalShadowRadius
    }

    /**
     * ShadowLayer needs Alpha value with color to properly render shadow.
     * This methods add alpha value to color if user doesn't provide it
     */
    private fun adjustAlpha() {
        if (Color.alpha(shadowColor) >= 255) {
            shadowColor = ColorUtils.setAlphaComponent(shadowColor, defaultShadowColorAlpha)
        } else {
            defaultShadowColorAlpha = Color.alpha(shadowColor)
        }
    }

    private fun generateShadowBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val canvas = Canvas(bitmap)
        val childRect = getChildRect()

        val shadowRect = RectF(
            childRect.left + dx,
            childRect.top + dy,
            childRect.right + dx,
            childRect.bottom + dy
        )

        val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.TRANSPARENT
            style = Paint.Style.FILL
        }

        shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor)

        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint)

        return bitmap
    }

    //alpha animation for shadow color
    private fun hideShadowAnimator(): ValueAnimator {
        val alphaAnimator = ValueAnimator.ofInt(defaultShadowColorAlpha, 0)
        alphaAnimator.addUpdateListener { animation ->
            shadowColor = ColorUtils.setAlphaComponent(shadowColor, animation.animatedValue as Int)
            updateBackground()
        }
        alphaAnimator.interpolator = AccelerateDecelerateInterpolator()
        alphaAnimator.startDelay = 100
        alphaAnimator.duration = 50
        return alphaAnimator
    }

    private fun showShadowAnimator(): ValueAnimator {
        val alphaAnimator = ValueAnimator.ofInt(0, defaultShadowColorAlpha)
        alphaAnimator.addUpdateListener { animation ->
            shadowColor = ColorUtils.setAlphaComponent(shadowColor, animation.animatedValue as Int)
            updateBackground()
        }
        alphaAnimator.interpolator = AccelerateDecelerateInterpolator()
        alphaAnimator.duration = 50
        return alphaAnimator
    }

    private fun updateBackground() {
        background = BitmapDrawable(resources, generateShadowBitmap())
    }

    private fun getChildRect(): Rect {
        val child = getChildAt(0)
        val shadowRadiusInt = shadowRadius.toInt()
        val effectiveHorizontalPadding = if (width == child.measuredWidth) {
            0
        } else {
            shadowRadiusInt
        }
        val effectiveVerticalPadding = if (height == child.measuredHeight) {
            0
        } else {
            shadowRadiusInt
        }
        val childLeft = if (shadowRegion.left) effectiveHorizontalPadding else 0
        val childTop = if (shadowRegion.top) effectiveVerticalPadding else 0
        return Rect(
            childLeft,
            childTop,
            childLeft + child.measuredWidth,
            childTop + child.measuredHeight
        )
    }

    enum class ShadowType {
        LOW,
        HIGH
    }
}
