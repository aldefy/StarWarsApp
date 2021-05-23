package com.star.wars.andromeda

enum class AndromedaTheme(val themeResId: Int, val isDarkTheme: Boolean) {
    LIGHT(R.style.Andromeda_BlueThemeLight, false),
    DARK(R.style.Andromeda_BlueThemeDark, true),
}
