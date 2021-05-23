package com.star.wars.andromeda.views.navbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.Group
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.*
import com.star.wars.andromeda.extensions.ContextualActionListener
import com.star.wars.andromeda.extensions.readAttributes
import com.star.wars.andromeda.tokens.icon_dynamic_default
import com.star.wars.andromeda.views.assets.icon.Icon
import com.star.wars.andromeda.views.button.AndromedaButton
import com.star.wars.andromeda.views.icons.AndromedaIconView
import com.star.wars.andromeda.views.icons.IconManager
import com.star.wars.andromeda.views.shadow.AndromedaShadowLayout

/***
 * Andromeda NavBar component (default style)
 *
 * xml usage
 * ```xml
 * <com.star.wars.andromeda.views.navbar.AndromedaNavBar
 *  android:layout_width="match_parent"
 *  android:layout_height="wrap_content"
 *  app:title="AndromedaNavBar title"
 *  app:subtitle="AndromedaNavBar subtitle"
 *  app:logo="@drawable/some_drawable"
 *  />
 *  ```
 *  AndromedaNavBar can be created in java/kotlin classes like any other view component
 *  constructors are identical to standard views, no extra params in constructor
 *
 *  Setting dynamic titles can be done via [setTitle] method.
 *  Similarly subtitle can be set via [setSubtitle]
 *  AndromedaNavBar logo can be set dynamically using [setNavigationLogo]
 *
 *  For showing back navigation icon and listening to the click of it
 *  [showNavigationIcon] cab be used
 *
 *  [inflateMenu] method can be used to show menu. When using inflateMenu with a menu xml resource, make sure the following convention is followed
 *
 * Use [setNavigateBackAccessibilityDescription] method or `app:navigate_back_accessibility_description`
 * attribute to set accessibility description for back button. For menu items, title will be
 * considered as accessibility description.
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
 *  Contextual action is supported using [setContextualAction] and [hideContextualAction]
 *
 *
 */
class AndromedaNavBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : AndromedaAbstractNavBar(
    context,
    attributeSet,
    defStyleRes
) {
    private lateinit var titleView: AndromedaNavBarTitleView

    private lateinit var menuView: AndromedaNavBarMenuView

    var divider = Divider.NONE
        set(value) {
            field = value
            updateDivider()
        }

    fun setNavigationLogo(iconName: Icon, tintColorToken: Int) {
        setNavigationLogo(IconManager.getIconDrawable(context, iconName, tintColorToken))
    }

    fun setNavigationLogo(logo: Drawable) {
        titleView.setLogo(logo)
    }

    fun hideNavigationLogo() {
        titleView.hideLogo()
    }

    fun hideNavigationIcon() {
        findViewById<Group>(R.id.navigation_logo_group).makeGone()
    }

    fun setTitle(title: CharSequence) {
        titleView.setTitle(title)
    }

    fun hideTitle() {
        titleView.hideTitle()
    }

    fun setSubtitle(subtitle: CharSequence) {
        titleView.setSubtitle(subtitle)
    }

    fun hideSubtitle() {
        titleView.hideSubtitle()
    }

    fun setContextualAction(action: String,
                            actionListener: ContextualActionListener) {
        findViewById<AndromedaButton>(R.id.navbar_contextual_action).apply {
            makeVisible()
            text = action
            setOnClickListener { actionListener() }
        }
    }

    fun hideContextualAction() {
        findViewById<AndromedaButton>(R.id.navbar_contextual_action).makeGone()
    }

    /**
     * sets the custom view implementation in the title region(From navigation logo to the start of menu icons)
     * @param customTitleView implementation of [AndromedaNavBarTitleView] that is to be used in the title region
     */
    fun setTitleView(customTitleView: AndromedaNavBarTitleView) {
        findViewById<FrameLayout>(R.id.title_view_container).removeAllViews()
        titleView = customTitleView
        addTitleView()
    }

    /**
     * sets the custom view implementation in the menu region(aligned at the right end of the navbar)
     * @param customTitleView implementation of [AndromedaNavBarMenuView] that is to be used in the menu region
     */
    fun setMenuView(customMenuView: AndromedaNavBarMenuView) {
        findViewById<FrameLayout>(R.id.navigation_menu_container).removeAllViews()
        menuView = customMenuView
        addMenuView()
    }

    /** @suppress **/
    override fun onFinishInflate() {
        super.onFinishInflate()
        addTitleView()
        addMenuView()
    }

    /**
     * @suppress
     */
    override fun getLayout(): Int {
        titleView = DefaultAndromedaNavBarTitleView(context)
        menuView = DefaultAndromedaNavBarMenuView(context)
        return R.layout.andromeda_navbar
    }

    /**
     * @suppress
     */
    override fun initAttributes(attributeSet: AttributeSet) {
        readAttributes(attributeSet, R.styleable.AndromedaNavBar) {
            setTitleInternal(it.getString(R.styleable.AndromedaNavBar_title))
            setSubtitleInternal(it.getString(R.styleable.AndromedaNavBar_subtitle))
            setLogoInternal(it.getDrawableCompat(context, R.styleable.AndromedaNavBar_logo))
            setNavigateBackAccessibilityDescription(
                it.getString(R.styleable.AndromedaNavBar_navigate_back_accessibility_description)
                    ?: resources.getString(R.string.accessibilityNavigateBack)
            )
        }
    }

    override fun showFirstMenuItem(menuItem: MenuItem, menuItemClickListener: OnMenuItemClickListener) {
        menuView.showFirstMenuItem(menuItem, menuItemClickListener)
    }

    override fun showSecondMenuItem(menuItem: MenuItem, menuItemClickListener: OnMenuItemClickListener) {
        menuView.showSecondMenuItem(menuItem, menuItemClickListener)
    }

    override fun setNavigationIcon(icon: Icon) {
        findViewById<AndromedaIconView>(R.id.navigation_back).setIcon(icon, icon_dynamic_default)
        findViewById<Group>(R.id.navigation_back_group).makeVisible()
    }

    fun setNavigateBackAccessibilityDescription(description: String) {
        findViewById<AndromedaIconView>(R.id.navigation_back).contentDescription = description
    }

    override fun getNavigationBackView() = findViewById<AndromedaIconView>(R.id.navigation_back)

    override fun hideMenu() {
        menuView.hideMenu()
    }

    /**
     * Provides the view that is displaying the right most menu icon in the navigation bar.
     * Depending on menu(s) are present or not, it could return null or the reference to menu view visible.
     * If you are using a custom [AndromedaNavBarMenuView] implementation, don't use this method to get the menu view reference
     */
    override fun getMenuView1(): View? = menuView.view.findViewById<View?>(R.id.navigation_menu_icon_1)

    /**
     * Provides the view that is displaying before the right most menu icon in the navigation bar.
     * Depending on menu(s) are present or not, it could return null or the reference to menu view visible.
     * If you are using a custom [AndromedaNavBarMenuView] implementation, don't use this method to get the menu view reference
     */
    override fun getMenuView2(): View? = menuView.view.findViewById<View?>(R.id.navigation_menu_icon_2)

    private fun setTitleInternal(title: String?) {
        if (null != title) {
            setTitle(title)
        }
    }

    private fun setSubtitleInternal(subTitle: String?) {
        if (null != subTitle) {
            setSubtitle(subTitle)
        }
    }

    private fun setLogoInternal(logo: Drawable?) {
        if (null != logo) {
            setNavigationLogo(logo)
        }
    }

    private fun addTitleView() {
        findViewById<FrameLayout>(R.id.title_view_container).addView(titleView.view)
    }

    private fun addMenuView() {
        findViewById<FrameLayout>(R.id.navigation_menu_container).addView(menuView.view)
    }


    private fun updateDivider() {
        when (divider) {
            Divider.SHADOW -> {
                findViewById<AndromedaShadowLayout>(R.id.navbar_shadow).enableShadow()
                findViewById<View>(R.id.navbar_divider).makeGone()
            }
            Divider.LINE -> {
                findViewById<AndromedaShadowLayout>(R.id.navbar_shadow).disableShadow()
                findViewById<View>(R.id.navbar_divider).makeVisible()
            }
            Divider.NONE -> {
                findViewById<AndromedaShadowLayout>(R.id.navbar_shadow).disableShadow()
                findViewById<View>(R.id.navbar_divider).makeGone()
            }
        }
    }

    enum class Divider {
        NONE,
        LINE,
        SHADOW
    }
}
