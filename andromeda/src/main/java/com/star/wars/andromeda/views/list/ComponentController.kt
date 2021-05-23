package com.star.wars.andromeda.views.list

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.stickyheader.StickyHeaderCallbacks
import com.star.wars.andromeda.extensions.ViewComponentNotDrawnHandler
import com.star.wars.andromeda.views.list.internal.generateModel

class ComponentController(
    val deepLinkHandler: (String) -> Unit,
    val uncaughtViewData: ViewComponentNotDrawnHandler
) : TypedEpoxyController<List<ComponentData>>(), StickyHeaderCallbacks {

    override fun buildModels(data: List<ComponentData>) {
        data.map { componentData ->
            generateModel(
                data = componentData,
                deepLinkHandler = { deepLink ->
                    deepLinkHandler(deepLink)
                }, viewComponentNotDrawnHandler = { data ->
                    uncaughtViewData(data)
                }
            ).addTo(this)
        }
    }
}
