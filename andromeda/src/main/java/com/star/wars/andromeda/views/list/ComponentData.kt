package com.star.wars.andromeda.views.list

import android.os.Parcelable

interface ComponentData : Parcelable {
    val id: String
    val width: Width
    val height: Height
    val viewType: String
    val paddingHorizontal: Int
    val paddingVertical: Int
    val gravity: Gravity
}
