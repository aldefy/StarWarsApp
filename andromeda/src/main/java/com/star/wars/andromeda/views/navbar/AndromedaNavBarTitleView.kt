package com.star.wars.andromeda.views.navbar

import android.graphics.drawable.Drawable
import android.view.View

/**
 * Defines the behaviour of title view in the Andromeda navbar. Custom views which need to be added as title view should implement this behaviour
 */
interface AndromedaNavBarTitleView {

    /**
     * The view that has to be added in the title region
     */
    val view: View

    fun setLogo(drawable: Drawable)

    fun hideLogo()

    fun setTitle(title: CharSequence)

    fun hideTitle()

    fun setSubtitle(subtitle: CharSequence)

    fun hideSubtitle()
}
