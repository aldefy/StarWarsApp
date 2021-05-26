package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.Gravity
import com.star.wars.andromeda.views.list.internal.component.carousel.data.CarouselComponentData
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.Orientation
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupTypes
import com.star.wars.andromeda.views.text.TypographyStyle
import com.star.wars.details.model.DetailsFilmsCombinedResult
import com.star.wars.details.model.DetailsSpeciesCombinedResult
import javax.inject.Inject

interface DetailsFetchSpeciesTransformer {
    fun toSpeciesDetailsComponents(combinedResult: DetailsSpeciesCombinedResult): List<ComponentData>
}

class DetailsFetchSpeciesTransformerImpl @Inject constructor() : DetailsFetchSpeciesTransformer {
    override fun toSpeciesDetailsComponents(combinedResult: DetailsSpeciesCombinedResult): List<ComponentData> {
        val element = CarouselComponentData(
            id = "films",
            children = mutableListOf(
                ViewGroupComponentData(
                    id = "subviewgroup-films",
                    children = combinedResult.species.mapIndexed { index, result ->
                        ViewGroupComponentData(
                            id = "subviewgroup-species-$index",
                            children = mutableListOf(
                                TextComponentData(
                                    id = "planetTitle-planet$index",
                                    text = result.name,
                                    textStyle = TypographyStyle.TITLE_MODERATE_DEMI_DEFAULT,
                                    gravity = Gravity.START
                                ),
                                TextComponentData(
                                    id = "species$index",
                                    text = result.language,
                                    textStyle = TypographyStyle.BODY_MODERATE_DEFAULT,
                                    gravity = Gravity.START
                                )
                            ),
                            paddingHorizontal = 8,
                            paddingVertical = 8,
                            orientation = Orientation.VERTICAL,
                            type = ViewGroupTypes.LINEAR
                        )
                    }.toMutableList(),
                    paddingHorizontal = 8,
                    paddingVertical = 8,
                    orientation = Orientation.VERTICAL,
                    type = ViewGroupTypes.LINEAR
                )
            )
        )
        return listOf(element)
    }
}
