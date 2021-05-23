package com.star.wars.andromeda.views.navbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.MenuRes
import com.star.wars.andromeda.extensions.OnMenuItemClickListener
import com.star.wars.andromeda.views.assets.icon.Icon


/**
 * @suppress
 * Base class that provides and exposes the common functions of types of NavBars
 * DON'T use it for any UI related purposes, use concrete classes like [AndromedaNavBar] or [AndromedaTransparentNavBar]
 * based on the UI requirements
 */
abstract class AndromedaAbstractNavBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : FrameLayout(
    context,
    attributeSet,
    defStyleRes
) {
    init {
        initNavBar()
        attributeSet?.let(::initAttributes)
    }

    internal abstract fun initAttributes(attributeSet: AttributeSet)

    internal abstract fun getLayout(): Int

    internal abstract fun getNavigationBackView(): View

    internal abstract fun setNavigationIcon(icon: Icon)

    internal abstract fun showFirstMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    )

    internal abstract fun showSecondMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    )

    abstract fun hideMenu()

    abstract fun getMenuView1(): View?

    abstract fun getMenuView2(): View?


    fun showNavigationIcon(
        icon: Icon = Icon.NAVIGATION_BACK,
        listener: OnClickListener
    ) {
        setNavigationIcon(icon)
        getNavigationBackView().setOnClickListener(listener)
    }

    fun inflateMenu(
        @MenuRes menuResId: Int,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        val menu = MenuResourceParser(context).parse(menuResId)
        inflateMenu(menu, menuItemClickListener)
    }

    fun inflateMenu(
        menuItems: List<MenuItem>,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        if (menuItems.isNotEmpty()) {
            showFirstMenuItem(
                menuItems[0],
                menuItemClickListener
            )
            if (menuItems.size > 1) {
                showSecondMenuItem(
                    menuItems[1],
                    menuItemClickListener
                )
            }
        }
    }

    private fun initNavBar() {
        LayoutInflater.from(context).inflate(
            getLayout(),
            this,
            true
        )
    }
}
