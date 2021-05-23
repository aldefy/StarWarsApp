package com.star.wars.andromeda.views.navbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.star.wars.andromeda.AndromedaAttributeManager
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.*
import com.star.wars.andromeda.tokens.icon_dynamic_default
import com.star.wars.andromeda.views.assets.icon.Icon
import com.star.wars.andromeda.views.button.AndromedaCircularButton
import com.star.wars.andromeda.views.button.AndromedaCircularButton.CircularButtonType
import com.star.wars.andromeda.views.icons.IconData

/*
* Andromeda NavBar Component (Transparent Style)
*
* xml usage
* ```xml
* <com.star.wars.andromeda.views.navbar.AndromedaTransparentNavBar
*  android:layout_width="match_parent"
*  android:layout_height="wrap_content"
*  />
*  ```
*  AndromedaTransparentNavBar can be created in java/kotlin classes like any other view component
*  constructors are identical to standard views, no extra params in constructor
*
*  AndromedaTransparentNavBar doesn't show title, subtitle and logo
*
*  For showing back navigation icon and listening to the click of it
*  [showNavigationIcon] cab be used
*
*  [inflateMenu] method can be used to show menu
*
*  * id attribute of the menu item is used with android namespace, not app
*  * icon_name and icon_color_token properties are used with app name space, not android
*    eg:
*    ```xml
*      <menu>
*         <item
*            android:id="@+id/some_id"
*            app:icon_name="icon_name"
*            app:icon_color_token="icon_color_token"/>
*      </menu>
*    ```
*
*  Contextual action is also not supported in this style
*
*/

class AndromedaTransparentNavBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : AndromedaAbstractNavBar(
    context,
    attributeSet,
    defStyleRes
) {
    private lateinit var navigationBack: AndromedaCircularButton
    private lateinit var navigationMenuIcon1: AndromedaCircularButton
    private lateinit var navigationMenuIcon2: AndromedaCircularButton

    /**
     * @suppress
     */
    override fun initAttributes(attributeSet: AttributeSet) {
        navigationBack = findViewById(R.id.transparent_navigation_back)
        navigationMenuIcon1 = findViewById(R.id.transparent_navigation_menu_icon_1)
        navigationMenuIcon2 = findViewById(R.id.transparent_navigation_menu_icon_2)
        readAttributes(
            attributeSet,
            R.styleable.AndromedaTransparentNavBar
        ) {
            setNavigateBackAccessibilityDescription(
                it.getStringViaResources(R.styleable.AndromedaTransparentNavBar_navigate_back_accessibility_description)
                    ?: resources.getString(R.string.accessibilityNavigateBack)

            )
        }

    }

    /**
     * @suppress
     */
    override fun getLayout() = R.layout.andromeda_navbar_transparent

    override fun getNavigationBackView() = navigationBack

    override fun setNavigationIcon(icon: Icon) {
        navigationBack.apply {
            init(
                CircularButtonType.CIRCULAR_SECONDARY_REGULAR,
                IconData(icon, icon_dynamic_default)
            )
            makeVisible()
        }
    }

    override fun showFirstMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        bindMenuItem(
            navigationMenuIcon1,
            menuItem,
            menuItemClickListener
        )
    }

    override fun showSecondMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        bindMenuItem(
            navigationMenuIcon2,
            menuItem,
            menuItemClickListener
        )
    }

    override fun hideMenu() {
        (navigationMenuIcon1).makeGone()
        (navigationMenuIcon2).makeGone()
    }

    /**
     * Provides the view that is displaying the right most menu icon in the navigation bar.
     * Depending on menu(s) are present or not, it could return null or the reference to menu view visible.
     */
    override fun getMenuView1(): View? = findViewById(R.id.navigation_menu_icon_1)

    /**
     * Provides the view that is displaying the right most menu icon in the navigation bar.
     * Depending on menu(s) are present or not, it could return null or the reference to menu view visible.
     */
    override fun getMenuView2(): View? = findViewById(R.id.navigation_menu_icon_2)

    private fun bindMenuItem(
        menuView: AndromedaCircularButton,
        menuItem: MenuItem,
        clickListener: OnMenuItemClickListener
    ) {
        menuView.apply {
            init(
                circularButtonType = CircularButtonType.CIRCULAR_SECONDARY_REGULAR,
                iconData = IconData(
                    menuItem.icon,
                    AndromedaAttributeManager.getColorFromAttribute(
                        context,
                        menuItem.iconColorToken
                    )
                )
            )
            if (menuItem.title != null) setAccessibilityDescription(menuItem.title)
            setOnClickListener { clickListener(menuItem.id) }
            makeVisible()
        }
    }

    fun setNavigateBackAccessibilityDescription(description: String) {
        (navigationBack).setAccessibilityDescription(description)
    }
}


