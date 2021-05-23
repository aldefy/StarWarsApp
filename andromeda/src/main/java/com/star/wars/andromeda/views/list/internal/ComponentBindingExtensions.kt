package com.star.wars.andromeda.views.list.internal

import com.airbnb.epoxy.EpoxyModel
import com.star.wars.andromeda.extensions.ViewComponentNotDrawnHandler
import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.internal.component.empty.EmptyComponent_
import com.star.wars.andromeda.views.list.internal.component.text.TextComponent_
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData

fun generateModel(
    data: ComponentData,
    deepLinkHandler: (String) -> Unit,
    viewComponentNotDrawnHandler: ViewComponentNotDrawnHandler
): EpoxyModel<*> {
    return when (data) {
        is TextComponentData -> {
            TextComponent_()
                .id(data.id)
                .textComponentData(data)
                .deepLinkHandler(deepLinkHandler)
        }
        else -> {
            EmptyComponent_().id(data.id).also {
                viewComponentNotDrawnHandler(data)
            }
        }
    }
}
