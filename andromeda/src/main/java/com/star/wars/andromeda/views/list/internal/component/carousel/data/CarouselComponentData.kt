package com.star.wars.andromeda.views.list.internal.component.carousel.data

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.Gravity
import com.star.wars.andromeda.views.list.Height
import com.star.wars.andromeda.views.list.Width
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarouselComponentData(
    override val id: String = "",
    override val width: Width = Width.FILL,
    override val height: Height = Height.WRAP,
    override val gravity: Gravity = Gravity.NO_GRAVITY,
    override val viewType: String = "carousel",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    val children: List<ComponentData> = emptyList()
) : ComponentData
