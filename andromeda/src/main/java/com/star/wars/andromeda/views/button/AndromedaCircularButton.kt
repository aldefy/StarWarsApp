package com.star.wars.andromeda.views.button

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.animation.PathInterpolatorCompat
import com.star.wars.andromeda.AndromedaAttributeManager
import com.star.wars.andromeda.R
import com.star.wars.andromeda.databinding.AndromedaCircularButtonBinding
import com.star.wars.andromeda.exception.ColorTokenException
import com.star.wars.andromeda.extensions.*
import com.star.wars.andromeda.extensions.getDrawableCompat
import com.star.wars.andromeda.extensions.readAttributes
import com.star.wars.andromeda.tokens.fill_background_primary
import com.star.wars.andromeda.tokens.fill_background_tertiary
import com.star.wars.andromeda.tokens.spacing_4x
import com.star.wars.andromeda.tokens.spacing_6x
import com.star.wars.andromeda.views.assets.icon.Icon
import com.star.wars.andromeda.views.icons.IconData
import com.star.wars.andromeda.views.icons.IconManager
import android.graphics.Color as AndroidColor

/**
 * Andromeda Circular Button Component
 *
 * XML Usage:
 * ```xml
 * <com.star.wars.andromeda.views.button.AndromedaCircularButton
 *   android:layout_width="wrap_content"
 *   android:layout_height="wrap_content"
 *   android:layout_gravity="center"
 *   app:enabled="false"
 *   app:icon_name="communication_24_call"
 *   app:icon_color_token="?attr/icon_static_white"
 *   app:circular_btn_type="circular_primary_regular" />
 * ```
 *
 * Kotlin Usage:
 * ```kotlin
 * val circularButton = AndromedaCircularButton(context = this)
 * circularButton.init(CircularButtonType.CIRCULAR_SECONDARY_REGULAR, IconData(Icon.COMMUNICATION_24_SOLID_IC_CALL, icon_static_white))
 * // Add to parent view
 * ```
 *
 * You can enable or disable the button using [setEnabled] method or setting the xml
 * attribute `app:enabled` to `true` or `false` respectively.
 *
 * You can set accessibility text for loading state of a button using
 * [setLoadingStateContentDescription] method or `app:loading_state_content_description` attribute.
 *
 * You can set custom content description using
 * [setAccessibilityDescription] method or `app:accessibility_description`
 *
 * Andromeda Circular Button is a FrameLayout. To access internal buttons class, use [circularBtnInternal] but only if
 * absolutely necessary.
 *
 */

class AndromedaCircularButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var binding: AndromedaCircularButtonBinding =
        AndromedaCircularButtonBinding.inflate(LayoutInflater.from(context), this)
    val circularBtnInternal: AppCompatButton by lazy(LazyThreadSafetyMode.NONE) { binding.circularBtn }
    private var iconDrawable: Drawable? = null
    var isLoading: Boolean = false

    enum class CircularButtonType {
        CIRCULAR_PRIMARY_LARGE,

        CIRCULAR_PRIMARY_REGULAR,

        CIRCULAR_SECONDARY_REGULAR,

        CIRCULAR_WHITE_PRIMARY,

        CIRCULAR_GREY_PRIMARY
    }

    init {
        readAttributes(attrs, R.styleable.AndromedaCircularButton) { typedArray ->
            circularBtnInternal.isEnabled =
                typedArray.getBoolean(R.styleable.AndromedaCircularButton_enabled, true)
            val iconValue = typedArray.getInt(R.styleable.AndromedaCircularButton_icon_name, -1)
            val iconColorToken =
                typedArray.getColor(R.styleable.AndromedaCircularButton_icon_color_token, 0)
            val isLoading =
                typedArray.getBoolean(R.styleable.AndromedaCircularButton_loading, false)
            val loadingStateContentDescription =
                typedArray.getString(R.styleable.AndromedaCircularButton_loading_state_content_description)
                    ?: resources.getString(R.string.accessibilityButtonLoadingText)
            val accessibilityDescription =
                typedArray.getString(R.styleable.AndromedaCircularButton_accessibility_description)
            if (accessibilityDescription != null) {
                setAccessibilityDescription(accessibilityDescription)
            }
            setLoadingStateContentDescription(loadingStateContentDescription)
            if (iconValue != -1) {
                val iconName = Icon.values()[iconValue]
                if (iconColorToken == 0) throw ColorTokenException(iconName.name)
                addIcon(IconData(iconName, iconColorToken))
            }
            setButtonType(
                CircularButtonType.values()[typedArray.getInt(
                    R.styleable.AndromedaCircularButton_circular_btn_type,
                    0
                )]
            )
            if (isLoading) {
                showLoader()
            } else {
                hideLoader()
            }
            setTouchListener()
        }
    }

    fun setAccessibilityDescription(description: String) {
        circularBtnInternal.contentDescription = description
    }

    fun setLoadingStateContentDescription(loadingStateContentDescription: String) {
        ViewCompat.setAccessibilityDelegate(
            circularBtnInternal,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    /**
                     * By default, screen readers like TalkBack read out "Button", if button has no text.
                     * Adding extra info "Loading" for loading state of Circular Button.
                     */
                    if (isLoading) {
                        info.text = loadingStateContentDescription
                    }
                }
            })
    }

    fun init(circularButtonType: CircularButtonType, iconData: IconData) {
        addIcon(iconData)
        setButtonType(circularButtonType)
    }

    fun showLoader() {
        circularBtnInternal.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        circularBtnInternal.isClickable = false
        binding.asLoadingIcon.alpha = 1f
        binding.asLoadingIcon.makeVisible()
        startLoadingAnimation()
        isLoading = true
    }

    fun hideLoader() {
        circularBtnInternal.makeVisible()
        val hideLoaderAnimation = ObjectAnimator.ofFloat(binding.asLoadingIcon, View.ALPHA, 1f, 0f)
        hideLoaderAnimation.duration = 83
        hideLoaderAnimation.doOnEnd {
            binding.asLoadingIcon.makeGone()
            circularBtnInternal.setCompoundDrawablesWithIntrinsicBounds(
                iconDrawable,
                null,
                null,
                null
            )
        }
        hideLoaderAnimation.start()

        circularBtnInternal.isClickable = true
        isLoading = false
    }

    private fun startLoadingAnimation() {
        binding.asLoadingIcon.visibility = View.VISIBLE
        val showLoaderAnimation =
            ObjectAnimator.ofFloat(
                binding.asLoadingIcon,
                View.TRANSLATION_Y,
                (72).toPxFloat(context),
                0f
            )
        showLoaderAnimation.duration = 167
        showLoaderAnimation.interpolator = PathInterpolatorCompat.create(0.30f, 0.28f, 0.61f, 1f)
        showLoaderAnimation.start()
    }

    fun setOnClickListener(buttonClickedListener: () -> Unit) {
        circularBtnInternal.setOnClickListener(object : DebounceClickListener() {
            override fun doClick(v: View) {
                buttonClickedListener.invoke()
            }
        })
    }

    /** @suppress **/
    override fun setOnClickListener(listener: OnClickListener?) {
        setOnClickListener { listener?.onClick(this) }
    }

    override fun setEnabled(enabled: Boolean) {
        circularBtnInternal.isEnabled = enabled
    }

    override fun isEnabled(): Boolean = circularBtnInternal.isEnabled

    private fun setButtonType(circularButtonType: CircularButtonType) {
        when (circularButtonType) {
            CircularButtonType.CIRCULAR_PRIMARY_LARGE -> {
                setCircularPrimaryLargeButtonStyle()
            }
            CircularButtonType.CIRCULAR_PRIMARY_REGULAR -> {
                setCircularPrimaryRegularButtonStyle()
            }
            CircularButtonType.CIRCULAR_SECONDARY_REGULAR -> {
                setCircularSecondaryRegularButtonStyle()
            }
            CircularButtonType.CIRCULAR_WHITE_PRIMARY -> {
                setCircularWhitePrimaryButtonStyle()
            }
            CircularButtonType.CIRCULAR_GREY_PRIMARY -> {
                setCircularGreyPrimaryButtonStyle()
            }
        }
    }

    private fun setCircularPrimaryLargeButtonStyle() {
        setButtonBackground(
            R.drawable.andromeda_circular_button_background,
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_active_primary),
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_pressed),
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_inactive_primary)
        )
        setButtonHeightAndWidth(width = 48f.toPxInt(context), height = 48f.toPxInt(context))
        iconDrawable?.let { drawable ->
            circularBtnInternal.setPadding(
                spacing_6x.toInt() - drawable.intrinsicWidth / 2,
                0,
                0,
                0
            )
        }
        binding.asLoadingIcon.setTint(fill_background_primary)
    }

    private fun setCircularPrimaryRegularButtonStyle() {
        setButtonBackground(
            R.drawable.andromeda_circular_button_background,
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_active_primary),
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_pressed),
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_inactive_primary)
        )
        setButtonHeightAndWidth(width = 32f.toPxInt(context), height = 32f.toPxInt(context))
        iconDrawable?.let { drawable ->
            circularBtnInternal.setPadding(
                spacing_4x.toInt() - drawable.intrinsicWidth / 2,
                0,
                0,
                0
            )
        }
        binding.asLoadingIcon.setTint(fill_background_primary)
    }

    private fun setCircularSecondaryRegularButtonStyle() {
        setButtonBackground(
            R.drawable.andromeda_circular_button_background,
            AndromedaAttributeManager.getColorFromAttribute(
                context,
                R.attr.fill_background_secondary
            ),
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_pressed),
            AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_inactive_primary)
        )
        val dimension = 40f.toPxInt(context)
        setButtonHeightAndWidth(width = dimension, height = dimension)
        iconDrawable?.let { drawable ->
            val paddingLeft = (dimension - drawable.intrinsicWidth) / 2
            circularBtnInternal.setPadding(paddingLeft, 0, 0, 0)
        }
        binding.asLoadingIcon.setTint(fill_background_tertiary)
    }

    private fun setCircularWhitePrimaryButtonStyle() {
        setButtonBackground(
            R.drawable.andromeda_circular_button_background,
            AndroidColor.WHITE,
            AndroidColor.parseColor("#59ffffff"),
            AndroidColor.parseColor("#e5e5e5")
        )
        val dimension = 48f.toPxInt(context)
        setButtonHeightAndWidth(width = dimension, height = dimension)
        iconDrawable?.let { drawable ->
            val paddingLeft = (dimension - drawable.intrinsicWidth) / 2
            circularBtnInternal.setPadding(paddingLeft, 0, 0, 0)
        }
        binding.asLoadingIcon.setTint(fill_background_primary)
    }

    private fun setCircularGreyPrimaryButtonStyle() {
        setButtonBackground(
            R.drawable.andromeda_circular_button_background,
            AndroidColor.GRAY,
            AndroidColor.LTGRAY,
            AndroidColor.parseColor("#e5e5e5")
        )
        val dimension = 48f.toPxInt(context)
        setButtonHeightAndWidth(width = dimension, height = dimension)
        iconDrawable?.let { drawable ->
            val paddingLeft = (dimension - drawable.intrinsicWidth) / 2
            circularBtnInternal.setPadding(paddingLeft, 0, 0, 0)
        }
        binding.asLoadingIcon.setTint(fill_background_primary)
    }

    private fun setButtonBackground(
        drawableRes: Int,
        activeColor: Int,
        pressedColor: Int,
        inactiveColor: Int
    ) {
        circularBtnInternal.background = getBackgroundColorStateListDrawable(
            drawableRes,
            activeColor,
            pressedColor,
            inactiveColor
        )
    }

    private fun setButtonHeightAndWidth(width: Int, height: Int) {
        val params = LayoutParams(circularBtnInternal.layoutParams)
        params.width = width
        params.height = height
        circularBtnInternal.layoutParams = params
    }

    private fun addIcon(iconData: IconData) {
        iconDrawable =
            IconManager.getIconDrawable(context, iconData.icon, iconData.iconColorToken)
        circularBtnInternal.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null)
    }

    private fun getBackgroundColorStateListDrawable(
        @DrawableRes drawableRes: Int,
        activeColor: Int,
        pressedColor: Int,
        inactiveColor: Int
    ): Drawable {
        val bgDrawableActive = context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        val bgDrawablePressed =
            context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        val bgDrawableInactive =
            context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        bgDrawableActive.setColor(activeColor)
        bgDrawablePressed.setColor(pressedColor)
        bgDrawableInactive.setColor(inactiveColor)
        val stateListDrawable = StateListDrawable()
        val layerDrawable = LayerDrawable(arrayOf(bgDrawableActive, bgDrawablePressed))
        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), layerDrawable)
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), bgDrawableInactive)
        stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), bgDrawableActive)

        return stateListDrawable
    }


    private fun downAnimation() {
        val scaleDown = ValueAnimator.ofFloat(1f, 0.78f)
        scaleDown.duration = 33
        scaleDown.addUpdateListener {
            val value = it.animatedValue as Float
            scaleX = value
            scaleY = value
        }
        scaleDown.doOnStart {
            hideShadow()
        }
        scaleDown.start()
    }

    private fun upAnimation() {
        val scaleUp = ValueAnimator.ofFloat(0.78f, 1f)
        scaleUp.duration = 83
        scaleUp.addUpdateListener {
            val value = it.animatedValue as Float
            scaleX = value
            scaleY = value
        }
        scaleUp.doOnEnd {
            showShadow()
        }
        scaleUp.start()
    }

    /** To get desired  press effect we will [hideShadow] in [downAnimation]
     * & [showShadow] in [upAnimation]
     * */
    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener() {
        circularBtnInternal.setOnTouchListener { _, event ->
            when {
                isLoading -> {
                    return@setOnTouchListener true
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    downAnimation()
                    return@setOnTouchListener false
                }
                event.action == MotionEvent.ACTION_UP -> {
                    upAnimation()
                    return@setOnTouchListener false
                }
                else -> return@setOnTouchListener true
            }
        }
    }

    private fun showShadow() {
        binding.shadowLayout.showShadow()
    }

    private fun hideShadow() {
        binding.shadowLayout.hideShadow()
    }
}
