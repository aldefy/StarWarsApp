package com.star.wars.andromeda.views.spinner

import android.content.Context
import android.graphics.ColorFilter
import android.util.AttributeSet
import androidx.core.view.setPadding
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.airbnb.lottie.LottieDrawable.RESTART
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.getEnum
import com.star.wars.andromeda.extensions.readAttributes
import com.star.wars.andromeda.extensions.toPxInt
import com.star.wars.andromeda.tokens.fill_active_primary
import com.star.wars.andromeda.tokens.spacing_x

/**
 * Andromeda Spinner Component
 *
 * XML usage
 * ```xml
 * <com.star.wars.andromeda.views.spinner.AndromedaSpinner
 *     android:id="@+id/andromeda_spinner_small"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     app:spinner_type="small"
 *     app:tint_color_token="?attr/fill_background_tertiary" />
 * ```
 *
 * Use `app:tint_color_token` or [setTint] to set color to spinner using Andromeda color tokens
 *
 * Use [setAccessibilityDescription] method or `app:accessibility_description` attribute to set accessibility
 * description
 */
class AndromedaSpinner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LottieAnimationView(context, attrs) {

    enum class SpinnerType {
        SMALL,
        BIG
    }

    private var spinnerType = SpinnerType.BIG

    private var spinnerAccessibilityDescription: String? = null
        set(value) {
            field = value
            contentDescription = value
                ?: resources.getString(R.string.accessibilityButtonLoadingText)
        }

    init {
        readAttributes(attrs, R.styleable.AndromedaSpinner) { typedArray ->
            setType(
                typedArray.getEnum(
                    R.styleable.AndromedaSpinner_spinner_type,
                    SpinnerType.values(),
                    spinnerType
                )
            )
            val colorToken = typedArray.getInt(R.styleable.AndromedaSpinner_tint_color_token, -1)
            if (colorToken != -1) {
                setTint(colorToken)
            } else {
                setTint(fill_active_primary)
            }
            spinnerAccessibilityDescription =
                typedArray.getString(R.styleable.AndromedaSpinner_accessibility_description)
        }

        isFocusable = true
    }

    fun setAccessibilityDescription(description: String) {
        spinnerAccessibilityDescription = description
    }

    /**
     * Use this method to change the appearance of spinner between [SpinnerType.SMALL] and [SpinnerType.BIG]
     * This method is intended to use when you are creating the spinner dynamically and want to change the type from the default value
     * If you are using xml to inflate the spinner, there is a property named spinner_type to set the type of the spinner
     * Calling this method in runtime will definitely change the type of spinner from one to other, but it will cause a change in size and causing a re-layout of screen
     */
    fun setTint(colorTokenValue: Int) {
        val filter = SimpleColorFilter(colorTokenValue)
        val keyPath = KeyPath("**")
        val callback = LottieValueCallback<ColorFilter>(filter)
        addValueCallback<ColorFilter>(keyPath, LottieProperty.COLOR_FILTER, callback)
    }

    fun setType(type: SpinnerType) {
        spinnerType = type
        when (spinnerType) {
            SpinnerType.SMALL -> {
                setAnimation(R.raw.indicator_small)
                setPadding(0)
            }
            SpinnerType.BIG -> {
                setAnimation(R.raw.indicator_large)
                setPadding(spacing_x.toInt())
            }
        }
        setMinAndMaxFrame(0, 120)
        repeatMode = RESTART
        repeatCount = INFINITE
        playAnimation()
        requestLayout()
    }

    /** @suppress **/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dimension = when (spinnerType) {
            SpinnerType.BIG -> 36F.toPxInt(context)
            SpinnerType.SMALL -> 19F.toPxInt(context)
        }
        setMeasuredDimension(dimension, dimension)
    }
}
