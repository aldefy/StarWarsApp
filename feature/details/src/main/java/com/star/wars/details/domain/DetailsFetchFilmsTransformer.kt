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
import javax.inject.Inject

interface DetailsFetchFilmsTransformer {
    fun toFilmsDetailsComponents(combinedResult: DetailsFilmsCombinedResult): List<ComponentData>
}

class DetailsFetchFilmsTransformerImpl @Inject constructor() : DetailsFetchFilmsTransformer {
    override fun toFilmsDetailsComponents(combinedResult: DetailsFilmsCombinedResult): List<ComponentData> {
        val element = CarouselComponentData(
            id = "films",
            children = mutableListOf(
                ViewGroupComponentData(
                    id = "subviewgroup-films",
                    children = combinedResult.films.mapIndexed { index, result ->
                        ViewGroupComponentData(
                            id = "subviewgroup-films-$index",
                            children = mutableListOf(
                                TextComponentData(
                                    id = "planetTitle-planet$index",
                                    text = result.title,
                                    textStyle = TypographyStyle.TITLE_MODERATE_DEMI_DEFAULT,
                                    gravity = Gravity.START
                                ),
                                TextComponentData(
                                    id = "filmcrawl$index",
                                    text = result.openingCrawl,
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
