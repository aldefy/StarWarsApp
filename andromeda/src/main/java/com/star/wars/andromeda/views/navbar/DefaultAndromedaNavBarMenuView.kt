package com.star.wars.andromeda.views.navbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.star.wars.andromeda.AndromedaAttributeManager
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.OnMenuItemClickListener
import com.star.wars.andromeda.extensions.makeGone
import com.star.wars.andromeda.extensions.makeVisible
import com.star.wars.andromeda.views.icons.AndromedaIconView

open class DefaultAndromedaNavBarMenuView(context: Context) : AndromedaNavBarMenuView {

    open fun getLayout() = R.layout.andromeda_navbar_menu_view_layout

    override val view: View = LayoutInflater.from(context).inflate(getLayout(), null)

    override fun showFirstMenuItem(menuItem: MenuItem, menuItemClickListener: OnMenuItemClickListener) {
        bindMenuItem(
                view.findViewById(R.id.navigation_menu_icon_1),
                menuItem,
                menuItemClickListener
        )
    }

    override fun showSecondMenuItem(menuItem: MenuItem, menuItemClickListener: OnMenuItemClickListener) {
        bindMenuItem(
                view.findViewById(R.id.navigation_menu_icon_2),
                menuItem,
                menuItemClickListener
        )
    }

    override fun hideMenu() {
        view.findViewById<AndromedaIconView>(R.id.navigation_menu_icon_1).makeGone()
        view.findViewById<AndromedaIconView>(R.id.navigation_menu_icon_2).makeGone()
    }

    private fun bindMenuItem(menuView: AndromedaIconView,
                             menuItem: MenuItem,
                             clickListener: OnMenuItemClickListener) {
        menuView.apply {
            setIcon(
                    menuItem.icon,
                    AndromedaAttributeManager.getColorFromAttribute(
                            context,
                            menuItem.iconColorToken
                    )
            )
            contentDescription = menuItem.title
            setOnClickListener { clickListener(menuItem.id) }
            makeVisible()
        }
    }
}
