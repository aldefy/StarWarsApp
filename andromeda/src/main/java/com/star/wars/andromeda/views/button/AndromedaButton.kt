package com.star.wars.andromeda.views.button

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.animation.doOnEnd
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.star.wars.andromeda.AndromedaAttributeManager
import com.star.wars.andromeda.R
import com.star.wars.andromeda.databinding.AndromedaButtonBinding
import com.star.wars.andromeda.exception.ColorTokenException
import com.star.wars.andromeda.extensions.*
import com.star.wars.andromeda.tokens.spacing_3x
import com.star.wars.andromeda.tokens.spacing_4x
import com.star.wars.andromeda.tokens.spacing_x
import com.star.wars.andromeda.views.assets.icon.Icon
import com.star.wars.andromeda.views.icons.AndromedaIconView
import com.star.wars.andromeda.views.icons.IconData
import com.star.wars.andromeda.views.text.setStyle
import kotlin.math.roundToInt


/**
 * Andromeda Button Component
 *
 * Types
 * - primary_positive_regular (default)
 * - primary_positive_tiny
 * - primary_positive_full_width
 * - primary_destructive_regular
 * - primary_destructive_tiny
 * - primary_destructive_full_width
 * - secondary_positive_regular
 * - secondary_positive_tiny
 * - secondary_destructive_regular
 * - secondary_destructive_tiny
 * - secondary_static_white_regular
 * - secondary_static_white_tiny
 * - tertiary_positive_regular
 * - tertiary_positive_tiny
 * - tertiary_destructive_regular
 * - tertiary_destructive_tiny
 *
 * XML Usage:
 *
 * `Full Width Buttons`
 * - layout_width must be set to match_parent and height to wrap_content
 *
 * ```xml
 * <com.star.wars.andromeda.views.button.AndromedaButton
 *  android:layout_width="match_parent"
 *  android:layout_height="wrap_content"
 *  app:enabled="true"
 *  app:text="Order Now"
 *  app:btn_type="primary_positive_full_width" />
 * ```
 *
 * `Regular Buttons`
 * - layout_width must be set to match_parent and height to wrap_content.
 * - Add margin of ?attr/spacing_4x to left and right.
 * - Can optionally add an icon to the left of text.
 *
 * ```xml
 * <com.star.wars.andromeda.views.button.AndromedaButton
 *  android:layout_width="match_parent"
 *  android:layout_height="wrap_content"
 *  android:layout_marginLeft="?attr/spacing_4x"
 *  android:layout_marginRight="?attr/spacing_4x"
 *  app:enabled="true"
 *  app:text="Order Now"
 *  app:icon_name="navigation_16_next_ios"
 *  app:icon_color_token="?attr/icon_static_white"
 *  app:btn_type="primary_positive_regular" />
 * ```
 *
 * `Tiny Buttons`
 * - layout_width must be set to wrap and height to wrap_content
 *
 * ```xml
 * <com.star.wars.andromeda.views.button.AndromedaButton
 *  android:layout_width="wrap_content"
 *  android:layout_height="wrap_content"
 *  app:enabled="true"
 *  app:text="Order Now"
 *  app:btn_type="primary_positive_tiny" />
 * ```
 *
 * Kotlin Usage:
 *
 * ```kotlin
 * val andromedaButton = AndromedaButton(this)
 * andromedaButton.init(
 *          buttonType = AndromedaButton.ButtonType.PRIMARY_POSITIVE_REGULAR,
 *          iconData = IconData(Icon.NAVIGATION_16_NEXT_IOS, icon_static_white),
 *          text = "Order Now"
 * )
 * // Add to parent view
 * ```
 *
 * You can enable or disable the button using [setEnabled] method or setting the xml
 * attribute `app:enabled` to `true` or `false` respectively.
 *
 * You can init and hide the loader using [showLoader] and [hideLoader] methods or
 * setting the xml attribute `app:loading` to `true` or `false` respectively.
 *
 * You can set the text of the button  by setting value of  [text] property or setting the xml
 * attribute `app:text`.
 *
 * You can set icon on the button using [setButtonIcon] method or setting the xml attribute
 * `app:icon_name` and `app:icon_color_token`.
 *
 * You can set accessibility text for loading state of a button using
 * [setLoadingStateContentDescription] method or `app:loading_state_content_description` attribute.
 *
 * You can set custom content description using
 * [setAccessibilityDescription] method or `app:accessibility_description`
 *
 */

class AndromedaButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var bgRadius: Float = 0.0f
    private var isLoading = false
    private var currentText: CharSequence = ""
    private var iconDrawable: Drawable? = null
    private var buttonType: ButtonType = ButtonType.PRIMARY_POSITIVE_REGULAR
    private var buttonIconView: AndromedaIconView? = null
    private val binding: AndromedaButtonBinding by lazy {
        AndromedaButtonBinding.inflate(LayoutInflater.from(context), this)
    }

    // Must be ordered as AndromedaButton attributes in attrs.xml
    enum class ButtonType {
        PRIMARY_POSITIVE_REGULAR,

        PRIMARY_POSITIVE_TINY,

        PRIMARY_POSITIVE_FULL_WIDTH,

        PRIMARY_DESTRUCTIVE_REGULAR,

        PRIMARY_DESTRUCTIVE_TINY,

        PRIMARY_DESTRUCTIVE_FULL_WIDTH,

        SECONDARY_POSITIVE_REGULAR,

        SECONDARY_POSITIVE_TINY,

        SECONDARY_DESTRUCTIVE_REGULAR,

        SECONDARY_DESTRUCTIVE_TINY,

        SECONDARY_STATIC_WHITE_REGULAR,

        SECONDARY_STATIC_WHITE_TINY,

        TERTIARY_POSITIVE_REGULAR,

        TERTIARY_POSITIVE_TINY,

        TERTIARY_DESTRUCTIVE_REGULAR,

        TERTIARY_DESTRUCTIVE_TINY
    }

    init {
        binding
        readAttributes(attrs, R.styleable.AndromedaButton) { typedArray ->
            isLoading = typedArray.getBoolean(R.styleable.AndromedaButton_loading, false)
            isEnabled = typedArray.getBoolean(R.styleable.AndromedaButton_enabled, true)
            bgRadius = typedArray.getFloat(R.styleable.AndromedaButton_radius, 0.0f)
            val loadingStateContentDescription = typedArray.getString(
                R.styleable.AndromedaButton_loading_state_content_description
            ) ?: resources.getString(R.string.accessibilityButtonLoadingText)
            val accessibilityDescription =
                typedArray.getString(R.styleable.AndromedaButton_accessibility_description)
            if (accessibilityDescription != null) {
                setAccessibilityDescription(accessibilityDescription)
            }
            setLoadingStateContentDescription(loadingStateContentDescription)
            binding.tvText.text =
                typedArray.getString(R.styleable.AndromedaButton_text)
            val buttonTypeInt = typedArray.getInt(R.styleable.AndromedaButton_btn_type, 0)
            setButtonStyle(ButtonType.values()[buttonTypeInt])
            onTouchEvent()
            val iconValue = typedArray.getInt(R.styleable.AndromedaButton_icon_name, -1)
            val iconColorToken = typedArray.getInt(R.styleable.AndromedaButton_icon_color_token, 0)
            if (iconValue != -1) {
                val iconName = Icon.values()[iconValue]
                if (iconColorToken == 0) throw ColorTokenException(iconName.name)
                addIcon(IconData(iconName, iconColorToken), IconPosition.LEFT)
            }
            if (isLoading) {
                showLoader()
            }
        }
    }

    fun setAccessibilityDescription(description: String) {
        binding.llButtonContainer.contentDescription = description
    }

    fun setLoadingStateContentDescription(description: String) {
        ViewCompat.setAccessibilityDelegate(
            binding.llButtonContainer,
            object : AccessibilityDelegateCompat() {
                override fun dispatchPopulateAccessibilityEvent(
                    host: View,
                    event: AccessibilityEvent
                ): Boolean {
                    /**
                     * Instead of setting and resetting content description everywhere where button loading state
                     * changes, we can set accessibility delegate and add [accessibilityLoadingText], so that screen
                     * readers uses this event text only when an [AccessibilityEvent] happens and later
                     * restores the content description back to its normal state.
                     * The delegate has been set to [ll_button_container] instead of [this] or [as_loading_bar] for following
                     * reasons.
                     * 1. To set accessibility delegate to [this], this root view should be focusable and clickable, which might
                     * make child views non-clickable.
                     * 2. As [as_loading_bar] size is small, loading state will not spoken out until user taps this tiny spinner.
                     * Hence it has been set to [ll_button_container] as it takes almost the entire area
                     */
                    if (isLoading()) {
                        event.run {
                            if ((eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED ||
                                        eventType == AccessibilityEvent.TYPE_VIEW_CLICKED)
                            ) {
                                text.add(description)
                            }
                        }
                    }

                    return super.dispatchPopulateAccessibilityEvent(host, event)
                }
            })
    }

    private fun setButtonStyle(buttonType: ButtonType) {
        this.buttonType = buttonType
        when (buttonType) {
            ButtonType.PRIMARY_POSITIVE_REGULAR, ButtonType.PRIMARY_DESTRUCTIVE_REGULAR -> {
                setPrimaryRegularButtonStyle()
            }
            ButtonType.PRIMARY_POSITIVE_TINY, ButtonType.PRIMARY_DESTRUCTIVE_TINY -> {
                setPrimaryTinyButtonStyle()
            }
            ButtonType.PRIMARY_POSITIVE_FULL_WIDTH, ButtonType.PRIMARY_DESTRUCTIVE_FULL_WIDTH -> {
                setPrimaryFullWidthButtonStyle()
            }
            ButtonType.SECONDARY_POSITIVE_REGULAR, ButtonType.SECONDARY_DESTRUCTIVE_REGULAR -> {
                setSecondaryRegularButtonStyle()
            }
            ButtonType.SECONDARY_POSITIVE_TINY, ButtonType.SECONDARY_DESTRUCTIVE_TINY -> {
                setSecondaryTinyButtonStyle()
            }
            ButtonType.SECONDARY_STATIC_WHITE_REGULAR -> {
                setSecondaryInvertedRegularButtonStyle()
            }
            ButtonType.SECONDARY_STATIC_WHITE_TINY -> {
                setSecondaryInvertedTinyButtonStyle()
            }
            ButtonType.TERTIARY_POSITIVE_REGULAR, ButtonType.TERTIARY_DESTRUCTIVE_REGULAR -> {
                setTertiaryRegularButtonStyle()
            }
            ButtonType.TERTIARY_POSITIVE_TINY, ButtonType.TERTIARY_DESTRUCTIVE_TINY -> {
                setTertiaryTinyButtonStyle()
            }
        }
    }

    private fun setPrimaryRegularButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_background)
        setWidthAndMargins(
            spacing_3x.roundToInt(),
            spacing_3x.roundToInt(),
            LayoutParams.MATCH_PARENT
        )
        setButtonTextAppearance(R.style.PrimaryRegular)
        setLoadingBarColor(R.attr.title_moderate_bold_static_white_color)
    }

    private fun setPrimaryFullWidthButtonStyle() {
        setButtonBackground(R.drawable.andromeda_regular_positive_full_width_button_background)
        setWidthAndMargins(
            spacing_3x.roundToInt(),
            spacing_3x.roundToInt(),
            LayoutParams.MATCH_PARENT
        )
        setButtonTextAppearance(R.style.PrimaryRegular)
        setLoadingBarColor(R.attr.title_moderate_bold_static_white_color)
    }

    private fun setPrimaryTinyButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_background)
        setWidthAndMargins(
            spacing_x.roundToInt(),
            spacing_x.roundToInt(),
            LayoutParams.WRAP_CONTENT
        )
        setButtonTextAppearance(R.style.PrimaryTiny)
        setLoadingBarColor(R.attr.title_tiny_bold_static_white_color)
        binding.llButtonContainer.updatePadding(
            left = spacing_4x.toInt(),
            right = spacing_4x.toInt()
        )
    }

    private fun setSecondaryRegularButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_border_background)
        setWidthAndMargins(
            spacing_3x.roundToInt(),
            spacing_3x.roundToInt(),
            LayoutParams.MATCH_PARENT
        )

        when (buttonType) {
            ButtonType.SECONDARY_POSITIVE_REGULAR -> {
                setButtonTextAppearance(R.style.SecondaryPositiveRegular)
                if (isEnabled) setLoadingBarColor(R.attr.title_small_bold_active_color)
            }
            ButtonType.SECONDARY_DESTRUCTIVE_REGULAR -> {
                setButtonTextAppearance(R.style.SecondaryDestructiveRegular)
                if (isEnabled) setLoadingBarColor(R.attr.title_small_bold_error_color)
            }
        }
    }

    private fun setSecondaryTinyButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_border_background)
        setWidthAndMargins(
            spacing_x.roundToInt(),
            spacing_x.roundToInt(),
            LayoutParams.WRAP_CONTENT
        )

        when (buttonType) {
            ButtonType.SECONDARY_POSITIVE_TINY -> {
                setButtonTextAppearance(R.style.SecondaryPositiveTiny)
                if (isEnabled) setLoadingBarColor(R.attr.title_tiny_bold_active_color)
            }
            ButtonType.SECONDARY_DESTRUCTIVE_TINY -> {
                setButtonTextAppearance(R.style.SecondaryDestructiveTiny)
                if (isEnabled) setLoadingBarColor(R.attr.title_tiny_bold_error_color)
            }
        }
        binding.llButtonContainer.updatePadding(
            left = spacing_4x.toInt(),
            right = spacing_4x.toInt()
        )
    }

    private fun setSecondaryInvertedRegularButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_border_background)
        setWidthAndMargins(
            spacing_3x.roundToInt(),
            spacing_3x.roundToInt(),
            LayoutParams.MATCH_PARENT
        )
        setButtonTextAppearance(R.style.SecondaryInvertedRegular)
        if (isEnabled) setLoadingBarColor(R.attr.title_small_bold_static_white_color)
    }

    private fun setSecondaryInvertedTinyButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_border_background)
        setWidthAndMargins(
            spacing_x.roundToInt(),
            spacing_x.roundToInt(),
            LayoutParams.WRAP_CONTENT
        )
        setButtonTextAppearance(R.style.SecondaryInvertedTiny)
        if (isEnabled) setLoadingBarColor(R.attr.title_tiny_bold_static_white_color)
        binding.llButtonContainer.updatePadding(
            left = spacing_4x.toInt(),
            right = spacing_4x.toInt()
        )
    }

    private fun setTertiaryRegularButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_background)
        setWidthAndMargins(
            spacing_3x.roundToInt(),
            spacing_3x.roundToInt(),
            LayoutParams.MATCH_PARENT
        )

        when (buttonType) {
            ButtonType.TERTIARY_POSITIVE_REGULAR -> {
                setButtonTextAppearance(R.style.TertiaryPositiveRegular)
                if (isEnabled) setLoadingBarColor(R.attr.title_small_bold_active_color)
            }
            ButtonType.TERTIARY_DESTRUCTIVE_REGULAR -> {
                setButtonTextAppearance(R.style.TertiaryDestructiveRegular)
                if (isEnabled) setLoadingBarColor(R.attr.title_small_bold_error_color)
            }
        }
    }

    private fun setTertiaryTinyButtonStyle() {
        setButtonBackground(R.drawable.andromeda_button_background)
        setWidthAndMargins(
            spacing_x.roundToInt(),
            spacing_x.roundToInt(),
            LayoutParams.WRAP_CONTENT
        )

        when (buttonType) {
            ButtonType.TERTIARY_POSITIVE_TINY -> {
                setButtonTextAppearance(R.style.TertiaryPositiveTiny)
                if (isEnabled) setLoadingBarColor(R.attr.title_tiny_bold_active_color)
            }
            ButtonType.TERTIARY_DESTRUCTIVE_TINY -> {
                setButtonTextAppearance(R.style.TertiaryDestructiveTiny)
                if (isEnabled) setLoadingBarColor(R.attr.title_tiny_bold_error_color)
            }
        }
        binding.llButtonContainer.updatePadding(
            left = spacing_4x.toInt(),
            right = spacing_4x.toInt()
        )
    }

    private fun getBackgroundColorStateListDrawable(
        @DrawableRes drawableRes: Int,
        activeColor: Int,
        pressedColor: Int,
        inactiveColor: Int
    ): Drawable {
        Log.d("Radius", "${bgRadius}")
        val bgDrawableActive = context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        bgDrawableActive.cornerRadius = bgRadius
        val bgDrawablePressed =
            context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        bgDrawablePressed.cornerRadius = bgRadius
        val bgDrawableInactive =
            context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        bgDrawableInactive.cornerRadius = bgRadius
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

    private fun getBackgroundStrokeStateListDrawable(
        @DrawableRes drawableRes: Int,
        activeColor: Int,
        inactiveColor: Int
    ): Drawable {
        val bgDrawableNormal = context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        bgDrawableNormal.cornerRadius = bgRadius
        val bgDrawableInactive =
            context.getDrawableCompat(drawableRes)!!.mutate() as GradientDrawable
        bgDrawableInactive.cornerRadius = bgRadius
        bgDrawableNormal.setStroke(1f.toPxInt(context), activeColor)
        bgDrawableInactive.setStroke(1f.toPxInt(context), inactiveColor)
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), bgDrawableInactive)
        stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), bgDrawableNormal)

        return stateListDrawable
    }

    private fun setButtonBackground(@DrawableRes drawableRes: Int) {
        when (buttonType) {
            ButtonType.PRIMARY_POSITIVE_REGULAR, ButtonType.PRIMARY_POSITIVE_TINY, ButtonType.PRIMARY_POSITIVE_FULL_WIDTH -> {
                val activeColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_active_primary
                )
                val pressedColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_pressed)
                val inactiveColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_inactive_primary
                )
                binding.llButtonContainer.background =
                    getBackgroundColorStateListDrawable(
                        drawableRes,
                        activeColor,
                        pressedColor,
                        inactiveColor
                    )
            }
            ButtonType.PRIMARY_DESTRUCTIVE_REGULAR, ButtonType.PRIMARY_DESTRUCTIVE_TINY, ButtonType.PRIMARY_DESTRUCTIVE_FULL_WIDTH -> {
                val activeColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_error_primary
                )
                val pressedColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_pressed)
                val inactiveColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_inactive_primary
                )
                binding.llButtonContainer.background =
                    getBackgroundColorStateListDrawable(
                        drawableRes,
                        activeColor,
                        pressedColor,
                        inactiveColor
                    )
            }
            ButtonType.SECONDARY_POSITIVE_REGULAR, ButtonType.SECONDARY_POSITIVE_TINY -> {
                val activeColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.border_active)
                val inactiveColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.border_inactive)
                binding.llButtonContainer.background =
                    getBackgroundStrokeStateListDrawable(drawableRes, activeColor, inactiveColor)
            }
            ButtonType.SECONDARY_DESTRUCTIVE_REGULAR, ButtonType.SECONDARY_DESTRUCTIVE_TINY -> {
                val activeColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.border_error)
                val inactiveColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.border_inactive)
                binding.llButtonContainer.background =
                    getBackgroundStrokeStateListDrawable(drawableRes, activeColor, inactiveColor)
            }
            ButtonType.SECONDARY_STATIC_WHITE_REGULAR, ButtonType.SECONDARY_STATIC_WHITE_TINY -> {
                val activeColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.border_static_white
                )
                val inactiveColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.border_inactive)
                binding.llButtonContainer.background =
                    getBackgroundStrokeStateListDrawable(drawableRes, activeColor, inactiveColor)
            }
            ButtonType.TERTIARY_POSITIVE_REGULAR, ButtonType.TERTIARY_POSITIVE_TINY -> {
                val activeColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_active_secondary
                )
                val pressedColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_pressed)
                val inactiveColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_inactive_secondary
                )
                binding.llButtonContainer.background =
                    getBackgroundColorStateListDrawable(
                        drawableRes,
                        activeColor,
                        pressedColor,
                        inactiveColor
                    )
            }
            ButtonType.TERTIARY_DESTRUCTIVE_REGULAR, ButtonType.TERTIARY_DESTRUCTIVE_TINY -> {
                val activeColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_error_secondary
                )
                val pressedColor =
                    AndromedaAttributeManager.getColorFromAttribute(context, R.attr.fill_pressed)
                val inactiveColor = AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    R.attr.fill_inactive_secondary
                )
                binding.llButtonContainer.background =
                    getBackgroundColorStateListDrawable(
                        drawableRes,
                        activeColor,
                        pressedColor,
                        inactiveColor
                    )
            }
        }
    }

    private fun setWidthAndMargins(topMargin: Int, bottomMargin: Int, width: Int) {
        binding.tvText.updateLayoutParams<LinearLayout.LayoutParams> {
            this.topMargin = topMargin
            this.bottomMargin = bottomMargin
        }
        binding.llButtonContainer.updateLayoutParams<LayoutParams> {
            this.width = width
        }
    }

    private fun setButtonTextAppearance(@StyleRes typographyStyle: Int) {
        binding.tvText.setStyle(typographyStyle)
    }

    private fun setLoadingBarColor(@AttrRes colorAttribute: Int) {
        binding.asLoadingBar.setTint(
            AndromedaAttributeManager.getColorFromAttribute(
                context,
                colorAttribute
            )
        )
    }

    private fun startLoadingAnimation() {
        with(binding.asLoadingBar) {
            alpha = 1f
            visibility = View.VISIBLE
            val showLoaderAnimation = ObjectAnimator.ofFloat(
                this,
                View.TRANSLATION_Y,
                (72).toPxFloat(context),
                0f
            )
            showLoaderAnimation.duration = 167
            showLoaderAnimation.interpolator =
                PathInterpolatorCompat.create(0.30f, 0.28f, 0.61f, 1f)
            val hideButtonTextAnimation = ValueAnimator.ofFloat(1f, 0f)
            hideButtonTextAnimation.addUpdateListener {
                val value = it.animatedValue as Float
                buttonIconView?.alpha = value
                binding.tvText.alpha = value
            }
            hideButtonTextAnimation.duration = 83
            val startAnimation = AnimatorSet()
            startAnimation.playTogether(showLoaderAnimation, hideButtonTextAnimation)
            startAnimation.start()
        }
    }

    private fun addIcon(iconData: IconData, position: IconPosition) {
        if (buttonType.name.contains("TINY").not()) {
            buttonIconView = when (position) {
                IconPosition.LEFT -> binding.iconLeft
                IconPosition.RIGHT -> binding.iconRight
            }
            buttonIconView?.apply {
                makeVisible()
                setIcon(iconData.icon, iconData.iconColorToken)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onTouchEvent() {
        binding.llButtonContainer.setOnTouchListener { _, event ->
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
                event.action == MotionEvent.ACTION_CANCEL -> {
                    upAnimation()
                    return@setOnTouchListener false
                }
                else -> return@setOnTouchListener true
            }
        }
    }

    private fun downAnimation() {
        val scaleDownText = ValueAnimator.ofFloat(1f, 0.95f)
        scaleDownText.addUpdateListener {
            val value = it.animatedValue as Float
            binding.tvText.scaleX = value
            binding.tvText.scaleY = value
            buttonIconView?.apply {
                scaleX = value
                scaleY = value
            }
        }
        val scaleDownButton = ValueAnimator.ofFloat(1f, 0.98f)
        scaleDownButton.addUpdateListener {
            val value = it.animatedValue as Float
            if (!buttonType.name.contains("FULL_WIDTH")) {
                binding.llButtonContainer.scaleX = value
                binding.llButtonContainer.scaleY = value
            }
        }
        val scaleDownAnimatorSet = AnimatorSet()
        scaleDownAnimatorSet.playTogether(scaleDownButton, scaleDownText)
        scaleDownAnimatorSet.duration = 33
        scaleDownAnimatorSet.start()
    }

    private fun upAnimation() {
        val scaleUpText = ValueAnimator.ofFloat(0.95f, 1f)
        scaleUpText.addUpdateListener {
            val value = it.animatedValue as Float
            binding.tvText.scaleX = value
            binding.tvText.scaleY = value
            buttonIconView?.apply {
                scaleX = value
                scaleY = value
            }
        }
        val scaleUpButton = ValueAnimator.ofFloat(0.98f, 1f)
        scaleUpText.addUpdateListener {
            val value = it.animatedValue as Float
            if (!buttonType.name.contains("FULL_WIDTH")) {
                binding.llButtonContainer.scaleX = value
                binding.llButtonContainer.scaleY = value
            }
        }
        val scaleUpAnimatorSet = AnimatorSet()
        scaleUpAnimatorSet.playTogether(scaleUpButton, scaleUpText)
        scaleUpAnimatorSet.duration = 83
        scaleUpAnimatorSet.start()
    }

    override fun setEnabled(isEnabled: Boolean) {
        binding.llButtonContainer.isEnabled = isEnabled
        binding.tvText.isEnabled = isEnabled
    }

    override fun isEnabled(): Boolean {
        return binding.llButtonContainer.isEnabled
    }

    fun isLoading(): Boolean = isLoading

    fun setOnClickListener(buttonClickedListener: (() -> Unit)? = null) {
        binding.llButtonContainer.setOnClickListener(object :
            DebounceClickListener() {
            override fun doClick(v: View) {
                buttonClickedListener?.invoke()
            }
        })
        if (isLoading) {
            binding.llButtonContainer.isClickable = false
        }
    }

    fun showLoader() {
        if (binding.tvText.text.isNotEmpty()) {
            currentText = binding.tvText.text
            binding.tvText.text = ""
        }
        isLoading = true
        binding.llButtonContainer.isClickable = false
        startLoadingAnimation()
        val loadingBarWidth = 20f.toPxInt(context)
        if (buttonType.name.contains("TINY")) {
            binding.llButtonContainer.updatePadding(
                left = spacing_4x.toInt() + loadingBarWidth,
                right = spacing_4x.toInt()
            )
        }
    }

    fun hideLoader() {
        val hideLoaderAnimation = ObjectAnimator.ofFloat(
            binding.asLoadingBar,
            View.ALPHA,
            1f,
            0f
        )
        hideLoaderAnimation.duration = 83
        hideLoaderAnimation.doOnEnd {
            binding.asLoadingBar.visibility = View.GONE
        }
        val showButtonTextAnimation = ValueAnimator.ofFloat(0f, 1f)
        showButtonTextAnimation.addUpdateListener {
            val value = it.animatedValue as Float
            buttonIconView?.alpha = value
            binding.tvText.alpha = value
        }
        showButtonTextAnimation.duration = 83
        showButtonTextAnimation.startDelay = 67
        val startAnimation = AnimatorSet()
        startAnimation.playTogether(hideLoaderAnimation, showButtonTextAnimation)
        startAnimation.start()

        binding.tvText.text = currentText
        isLoading = false
        binding.llButtonContainer.isClickable = true
        if (buttonType.name.contains("TINY")) {
            binding.llButtonContainer.updatePadding(
                left = spacing_4x.toInt(),
                right = spacing_4x.toInt()
            )
        }
    }

    var text: CharSequence
        get() = binding.tvText.text as String
        set(value) {
            binding.tvText.text = value
        }

    fun setIcon(iconData: IconData, position: IconPosition) {
        addIcon(iconData, position)
    }

    fun init(
        buttonType: ButtonType,
        text: CharSequence,
        isEnabled: Boolean = true,
        isLoading: Boolean = false,
        iconData: IconData? = null,
        iconPosition: IconPosition = IconPosition.LEFT,
        radius: Float = 0.0f
    ) {
        bgRadius = radius
        iconData?.let { addIcon(it, iconPosition) }
        setButtonStyle(buttonType)
        setEnabled(isEnabled)
        onTouchEvent()
        binding.tvText.text = text
        this.isLoading = isLoading
        if (this.isLoading) {
            showLoader()
        }
    }
}

enum class IconPosition {
    LEFT,
    RIGHT
}
