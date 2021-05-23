package com.star.wars.andromeda.shimmer


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.getStringViaResources
import com.star.wars.andromeda.extensions.isVisible
import com.star.wars.andromeda.extensions.readAttributes
import com.star.wars.andromeda.tokens.border_static_white
import com.star.wars.andromeda.tokens.fill_mute_primary

/**
 * Adds a shimmer effect to any layout.
 *
 * This view currently works with just xml. To use it create a layout file on which you want
 * the shimmer to be shown. Add this layout to [AndromedaShimmer] using the `layout` attribute.
 *
 * Example usage:
 * ```
 * <com.star.wars.andromeda.shimmer.AndromedaShimmer
 *  android:layout_width="match_parent"
 *  android:layout_height="match_parent"
 *  app:layout="@layout/view_shimmer_demo" />
 * ```
 *
 * Note:
 * [AndromedaShimmer] works by changing the colours in the layout you pass to the grey colour you see
 * in the shimmer effect. So it's important that in the layout that you pass, the views whose shape
 * need to be visible in the shimmer effect need to have a background colour. The colors will be
 * overridden and [AndromedaShimmer] will use [centerColor] and [edgeColor] to draw the layout.
 *
 * [startShimmerAnimation] start/restart shimmer animation
 *
 * [stopShimmerAnimation] stop/pause shimmer animation
 *
 * ### Accessibility
 *
 * Use [setAccessibilityDescription] method or `app:accessibility_description` attribute to set accessibility
 * description.
 *
 */
class AndromedaShimmer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var animator: ValueAnimator? = null
    private var gradientPaint: Paint? = null
    private var gradientColors: IntArray? = null
    private val centerColor = border_static_white
    private val edgeColor = fill_mute_primary
    private val animationDuration = 1200L

    private var contentViewBitmap: Bitmap? = null
    private var contentViewPaint: Paint? = null
    private var contentViewRes: Int = -1
    private var contentView: View? = null
    private var shimmerBitmap: Bitmap? = null

    private var shimmerAccessibilityDescription: String? = null
        set(value) {
            field = value
            contentDescription = value
                ?: resources.getString(R.string.accessibilityButtonLoadingText)
        }

    init {
        readAttributes(attrs, R.styleable.AndromedaShimmer) { typedArray ->
            if (attrs != null) {
                contentViewRes = typedArray.getResourceId(R.styleable.AndromedaShimmer_layout, -1)
                if (contentViewRes == -1) {
                    throw IllegalAccessException("Cannot inflate shimmer view without a layout attribute")
                }

                shimmerAccessibilityDescription =
                    typedArray.getStringViaResources(R.styleable.AndromedaShimmer_accessibility_description)
            }
        }

        isFocusable = true
        initContentViewPaint()
        initGradientPaint()
        initAnimation()
    }

    fun setAccessibilityDescription(description: String) {
        shimmerAccessibilityDescription = description
    }

    fun startShimmerAnimation() {
        startAnimationIfNotAlreadyRunning()
    }

    fun stopShimmerAnimation() {
        updateGradient(width.toFloat(), -width.toFloat())
        cancelAnimation()
    }

    private fun initAnimation() {
        animator = ValueAnimator.ofFloat(-1f, 2f).apply {
            duration = animationDuration
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                val f = it.animatedValue as Float
                updateGradient(width.toFloat(), f)
                invalidate()
            }
        }
    }

    private fun initGradientPaint() {
        gradientPaint = Paint()
        gradientPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        gradientPaint!!.isAntiAlias = true
        gradientColors = intArrayOf(edgeColor, centerColor, edgeColor)
    }

    private fun initContentViewPaint() {
        contentViewPaint = Paint()
        contentViewPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
    }

    /**
     * @suppress
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (contentView == null) {
            contentView = initContentView(widthMeasureSpec, heightMeasureSpec)
        }

        val wms = MeasureSpec.makeMeasureSpec(contentView!!.width, MeasureSpec.EXACTLY)
        val hms = MeasureSpec.makeMeasureSpec(contentView!!.height, MeasureSpec.EXACTLY)
        super.onMeasure(wms, hms)
    }

    /**
     * @suppress
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateGradient(w.toFloat(), -1f)
        if (h <= 0 || w <= 0) {
            contentViewBitmap = null
            animator!!.cancel()
        }
    }

    private fun updateGradient(w: Float, animatedValue: Float) {
        //Both are zero, layout passes might be pending, too early to say invalid state
        if (width == 0 && height == 0) {
            return
        } else if (width == 0 || height == 0) {
            //either width or height is zero and other has value. Which indicates layout is done and one value is invalid
            throw IllegalStateException("width or height is zero. width = $width, height = $height")
        }
        val left = w * animatedValue
        val linearGradient = LinearGradient(
            left,
            0f,
            left + w,
            0f,
            requireNotNull(gradientColors),
            floatArrayOf(0f, .5f, 1f),
            Shader.TileMode.CLAMP
        )
        gradientPaint!!.shader = linearGradient
        if (shimmerBitmap == null) {
            shimmerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
        shimmerBitmap?.let { shimmerBitmap ->
            val canvas = Canvas(shimmerBitmap)
            contentViewBitmap?.let {
                canvas.drawBitmap(it, 0F, 0F, contentViewPaint)
                canvas.drawRect(
                    0F,
                    0F,
                    it.width.toFloat(),
                    it.height.toFloat(),
                    requireNotNull(gradientPaint)
                )
            }
        }
    }

    private fun initContentView(widthMeasureSpec: Int, heightMeasureSpec: Int): View {
        // Inflate content view and call measure and layout manually to get dimensions
        val contentView =
            LayoutInflater.from(context).inflate(contentViewRes, parent as ViewGroup, false)
        contentView.measure(widthMeasureSpec, heightMeasureSpec)
        contentView.layout(0, 0, contentView.measuredWidth, contentView.measuredHeight)

        if (contentView.measuredWidth == 0 || contentView.measuredHeight == 0) {
            throw IllegalStateException("Width or Height of shimmer content cannot be zero")
        } else {
            // Remove background, as shimmer show's up on the colored areas of the content view
            contentView.background = null

            // Draw the content view to a bitmap, we just need the alpha values of the bitmap here
            contentViewBitmap = try {
                Bitmap.createBitmap(
                    contentView.measuredWidth,
                    contentView.measuredHeight,
                    Bitmap.Config.ALPHA_8
                )
            } catch (e: OutOfMemoryError) {
                System.gc()
                Bitmap.createBitmap(
                    contentView.measuredWidth / 2,
                    contentView.measuredHeight / 2,
                    Bitmap.Config.ALPHA_8
                )
            }

            contentViewBitmap?.let { contentViewBitmap ->
                val canvas = Canvas(contentViewBitmap)
                contentView.draw(canvas)
            }
        }
        return contentView
    }

    /**
     * @suppress
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        shimmerBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }
    }

    /**
     * @suppress
     */
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when (visibility) {
            VISIBLE -> startAnimationIfNotAlreadyRunning()
            INVISIBLE, GONE -> cancelAnimation()
        }
    }

    /**
     * @suppress
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimationIfNotAlreadyRunning()
    }

    private fun cancelAnimation() {
        animator?.duration = 0
        animator?.cancel()
    }

    private fun startAnimationIfNotAlreadyRunning() {
        if (isVisible() && animator != null && !animator!!.isRunning) {
            animator!!.duration = animationDuration
            animator!!.start()
        }
    }

    /**
     * @suppress
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
