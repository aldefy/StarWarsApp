package com.star.wars.andromeda.views.list

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.stickyheader.StickyHeaderCallbacks
import com.star.wars.andromeda.extensions.ComponentClickHandler
import com.star.wars.andromeda.extensions.ViewComponentNotDrawnHandler
import com.star.wars.andromeda.views.list.internal.generateModel

class ComponentController(
    val componentClickHandler: ComponentClickHandler,
    val uncaughtViewData: ViewComponentNotDrawnHandler
) : TypedEpoxyController<List<ComponentData>>(), StickyHeaderCallbacks {

    override fun buildModels(data: List<ComponentData>) {
        data.map { componentData ->
            generateModel(
                data = componentData,
                componentClickHandler = { extra ->
                    componentClickHandler(extra)
                }, viewComponentNotDrawnHandler = { data ->
                    uncaughtViewData(data)
                }
            ).addTo(this)
        }
    }
}
