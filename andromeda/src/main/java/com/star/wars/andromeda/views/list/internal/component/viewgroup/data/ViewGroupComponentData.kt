package com.star.wars.andromeda.views.list.internal.component.viewgroup.data

import android.os.Parcelable
import com.star.wars.andromeda.Color
import com.star.wars.andromeda.views.list.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class ViewGroupComponentData(
    override val id: String = "",
    override val width: Width = Width.WRAP,
    override val height: Height = Height.WRAP,
    override val gravity: Gravity = Gravity.NO_GRAVITY,
    override val viewType: String = "viewgroup",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    override val extraPayload: Parcelable? = null,
    val children: MutableList<out BaseComponentData> = mutableListOf(),
    val type: ViewGroupTypes = ViewGroupTypes.LINEAR,
    val background: Color = Color.TRANSPARENT,
    val radius: Float = 4f,
    val orientation: Orientation = Orientation.VERTICAL,
    val marginsHorizontal: Int = 0,
    val marginsVertical: Int = 0,
    val cardElevation: Float? = 4.0f,
    val spanCount: Int = 3,
) : ComponentDataWithClick
