package com.star.wars.andromeda.views.list.internal.component.empty.data

import com.star.wars.andromeda.views.list.BaseComponentData
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmptyComponentData(
    override val id: String
) : BaseComponentData
