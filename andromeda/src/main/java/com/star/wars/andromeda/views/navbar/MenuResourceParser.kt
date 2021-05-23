package com.star.wars.andromeda.views.navbar

import android.content.Context
import android.content.res.XmlResourceParser
import com.star.wars.andromeda.R
import com.star.wars.andromeda.exception.ColorTokenException
import com.star.wars.andromeda.views.assets.icon.Icon

private const val MENU_ITEM_TAG = "item"
private const val ATTRIBUTE_ICON = "icon_name"

internal class MenuResourceParser(private val context: Context) {

    fun parse(menuRes: Int): List<MenuItem> {
        val menuList = mutableListOf<MenuItem>()
        val xmlParser = context.resources.getXml(menuRes)
        var tag = xmlParser.next()
        do {
            if (tag == XmlResourceParser.START_TAG  &&
                xmlParser.name == MENU_ITEM_TAG
            ) {
                menuList.add(getMenuItem(xmlParser))
            }
            tag = xmlParser.next()

        } while (tag != XmlResourceParser.END_DOCUMENT)
        return menuList
    }

    private fun getMenuItem(parser: XmlResourceParser): MenuItem {
        var title: String? = null
        var icon: String? = null
        var id = 0
        var colorToken: String? = null
        for (index in 0 until parser.attributeCount) {
            when (parser.getAttributeNameResource(index)) {
                android.R.attr.title -> title = getString(index, parser)
                R.attr.icon_name -> icon = parser.getAttributeValue(index)
                R.attr.icon_color_token -> colorToken = parser.getAttributeValue(index)
                android.R.attr.id -> id = parser.getAttributeResourceValue(index, 5)
            }
        }
        requireNotNull(icon) { "$ATTRIBUTE_ICON cannot be null or empty" }
        val iconEnum = Icon.values()[icon.toInt()]
        requireNotNull(colorToken) { ColorTokenException(iconEnum.name) }
        return MenuItem(
            id,
            title,
            iconEnum,
            colorToken.replace("?", "").toInt()
        )
    }

    private fun getString(index: Int, parser: XmlResourceParser): String {
        return try {
            context.getString(parser.getAttributeResourceValue(index, 0))
        } catch (exception: Exception) {
            parser.getAttributeValue(index)
        }
    }
}
