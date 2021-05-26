package com.star.wars.andromeda.views.list

import android.os.Parcelable

interface BaseComponentData: Parcelable {
    val id: String
}

interface ComponentData : BaseComponentData {
    val width: Width
    val height: Height
    val viewType: String
    val paddingHorizontal: Int
    val paddingVertical: Int
    val gravity: Gravity
    val extraPayload: Parcelable?
}
