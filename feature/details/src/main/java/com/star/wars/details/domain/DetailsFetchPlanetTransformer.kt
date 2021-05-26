package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.Gravity
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.Orientation
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupTypes
import com.star.wars.andromeda.views.text.TypographyStyle
import com.star.wars.details.model.PlanetsResponse
import javax.inject.Inject

interface DetailsFetchPlanetTransformer {
    fun toPlanetDetailsComponents(result: PlanetsResponse): List<ComponentData>
}

class DetailsFetchPlanetTransformerImpl @Inject constructor() : DetailsFetchPlanetTransformer {
    override fun toPlanetDetailsComponents(result: PlanetsResponse): List<ComponentData> {
        val element = ViewGroupComponentData(
            id = "planet",
            children = mutableListOf(
                ViewGroupComponentData(
                    id = "subviewgroup-planet",
                    children = mutableListOf(
                        TextComponentData(
                            id = "planetTitle-planet",
                            text = result.name,
                            textStyle = TypographyStyle.TITLE_MODERATE_DEMI_DEFAULT,
                            gravity = Gravity.START
                        ),
                        TextComponentData(
                            id = "characterBirthYear-planet",
                            text = result.population,
                            textStyle = TypographyStyle.BODY_MODERATE_DEFAULT,
                            gravity = Gravity.START
                        )
                    ),
                    paddingHorizontal = 8,
                    paddingVertical = 8,
                    orientation = Orientation.VERTICAL,
                    type = ViewGroupTypes.LINEAR
                )
            ),
            width = Width.FILL,
            marginsHorizontal = 16,
            marginsVertical = 8,
            type = ViewGroupTypes.CARD
        )
        return listOf(element)
    }
}
