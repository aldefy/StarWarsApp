package com.star.wars.andromeda.views.list.internal

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.star.wars.andromeda.extensions.ComponentClickHandler
import com.star.wars.andromeda.extensions.ViewComponentNotDrawnHandler
import com.star.wars.andromeda.extensions.dpToPixels
import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.internal.component.carousel.data.CarouselComponentData
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
    componentClickHandler: ComponentClickHandler,
    viewComponentNotDrawnHandler: ViewComponentNotDrawnHandler
): EpoxyModel<*> {
    return when (data) {
        is TextComponentData -> {
            TextComponent_()
                .id(data.id)
                .textComponentData(data)
                .componentClickHandler(componentClickHandler)
        }
        is ViewGroupComponentData -> {
            val models = data.children.map { dj ->
                generateModel(
                    data = dj,
                    componentClickHandler = componentClickHandler,
                    viewComponentNotDrawnHandler = viewComponentNotDrawnHandler
                )
            }
            when (data.type) {
                ViewGroupTypes.CARD -> CardGroupComponent_(models)
                    .id(data.id)
                    .viewGroup(data)
                    .componentClickHandler(componentClickHandler)
                ViewGroupTypes.LINEAR -> LinearGroupComponent_(models)
                    .id(data.id)
                    .viewGroupData(data)
                    .componentClickHandler(componentClickHandler)
                ViewGroupTypes.GRID -> GridComponent_()
                    .id(data.id)
                    .gridComponentData(data)
                    .componentClickHandler(componentClickHandler)
            }
        }
        is CarouselComponentData -> {
            val children = data.children.map { dj ->
                generateModel(
                    data = dj,
                    componentClickHandler = componentClickHandler,
                    viewComponentNotDrawnHandler = viewComponentNotDrawnHandler
                )
            }
            CarouselModel_()
                .id(data.id)
                .padding(
                    Carousel.Padding(
                        data.paddingHorizontal.dpToPixels(),
                        data.paddingVertical.dpToPixels(),
                        data.paddingHorizontal.dpToPixels(),
                        data.paddingVertical.dpToPixels(),
                        0
                    )
                )
                .models(children)
        }
        else -> {
            EmptyComponent_().id(data.id).also {
                viewComponentNotDrawnHandler(data.id)
            }
        }
    }
}
