package com.star.wars.andromeda.views.list.internal

import com.airbnb.epoxy.EpoxyModel
import com.star.wars.andromeda.extensions.ViewComponentNotDrawnHandler
import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.internal.component.empty.EmptyComponent_
import com.star.wars.andromeda.views.list.internal.component.text.TextComponent_
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.CardGroupComponent_
import com.star.wars.andromeda.views.list.internal.component.viewgroup.GridComponent_
import com.star.wars.andromeda.views.list.internal.component.viewgroup.LinearGroupComponent_
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupTypes

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
        is ViewGroupComponentData -> {
            val models = data.children.map { dj ->
                generateModel(
                    data = dj,
                    deepLinkHandler = deepLinkHandler,
                    viewComponentNotDrawnHandler = viewComponentNotDrawnHandler
                )
            }
            when (data.type) {
                ViewGroupTypes.CARD -> CardGroupComponent_(models)
                    .id(data.id)
                    .viewGroup(data)
                    .deepLinkHandler(deepLinkHandler)
                ViewGroupTypes.LINEAR -> LinearGroupComponent_(models)
                    .id(data.id)
                    .viewGroupData(data)
                    .deepLinkHandler(deepLinkHandler)
                ViewGroupTypes.GRID -> GridComponent_()
                    .id(data.id)
                    .gridComponentData(data)
                    .deepLinkHandler(deepLinkHandler)
            }
        }
        else -> {
            EmptyComponent_().id(data.id).also {
                viewComponentNotDrawnHandler(data)
            }
        }
    }
}
