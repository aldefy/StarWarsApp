package com.star.wars.andromeda.views.navbar

import androidx.annotation.IdRes
import com.star.wars.andromeda.views.assets.icon.Icon

data class MenuItem(
    @IdRes val id: Int,
    val title: String?,
    val icon: Icon,
    val iconColorToken: Int
)
