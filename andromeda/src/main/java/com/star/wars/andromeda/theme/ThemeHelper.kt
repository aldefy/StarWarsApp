package com.star.wars.andromeda.theme

import android.content.Context
import com.star.wars.andromeda.AndromedaTheme

const val PREFS_NAME: String = "ANDROMEDA_DEMO_PREFS"
const val THEME_KEY: String = "THEME"

fun getTheme(context: Context): AndromedaTheme {
    val savedKey =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(THEME_KEY, "")
    if (savedKey.isNullOrEmpty()) return AndromedaTheme.LIGHT
    return if (savedKey == "DEFAULT_BLUE") {
        AndromedaTheme.valueOf("BLUE_THEME_LIGHT")
    } else
        AndromedaTheme.valueOf(savedKey)
}

fun setTheme(context: Context, theme: AndromedaTheme) {
    context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(THEME_KEY, theme.name)
        .apply()
}
