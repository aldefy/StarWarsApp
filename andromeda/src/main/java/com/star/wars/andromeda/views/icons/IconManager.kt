package com.star.wars.andromeda.views.icons

import android.content.Context
import android.graphics.drawable.Drawable
import com.star.wars.andromeda.AndromedaAttributeManager
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.getDrawableCompat
import com.star.wars.andromeda.extensions.tint
import com.star.wars.andromeda.views.assets.icon.Icon
import com.star.wars.andromeda.views.icons.IconManager.getIconDrawable

/**
 * Andromeda Icon Manager
 *
 * [getIconDrawable] Get icon using iconName and tintColor
 * @see [Icon]
 */

object IconManager {

    fun getIconDrawable(context: Context, icon: Icon, tintColorToken: Int): Drawable {
        val drawable = context.getDrawableCompat(icon.getDrawableResId())
            ?: requireNotNull(context.getDrawableCompat(R.drawable.andromeda_icon_placeholder))
        drawable.mutate()
        return when (tintColorToken) {
            -1 -> {
                drawable.tint(
                    AndromedaAttributeManager.getColorFromAttribute(
                        context,
                        R.attr.icon_static_white
                    )
                )
            }
            -999 -> {
                drawable.tint(
                    AndromedaAttributeManager.getColorFromAttribute(
                        context,
                        R.attr.fill_active_primary
                    )
                )
            }
            else -> {
                drawable.tint(tintColorToken)
            }
        }
    }
}
