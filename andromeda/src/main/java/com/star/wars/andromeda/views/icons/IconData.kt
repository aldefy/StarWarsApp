package com.star.wars.andromeda.views.icons

import android.os.Parcelable
import com.star.wars.andromeda.views.assets.icon.Icon
import kotlinx.parcelize.Parcelize

@Parcelize
data class IconData(
    var icon: Icon,
    var iconColorToken: Int
) : Parcelable
