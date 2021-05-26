package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.Gravity
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.list.internal.component.carousel.data.CarouselComponentData
import com.star.wars.andromeda.views.list.internal.component.empty.data.EmptyComponentData
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.Orientation
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupTypes
import com.star.wars.andromeda.views.text.TypographyStyle
import com.star.wars.details.model.CharacterDetailsResponse
import javax.inject.Inject

interface DetailsFetchCharacterTransformer {
    fun toCharacterDetailsComponents(result: CharacterDetailsResponse): List<ComponentData>
}

const val HeightFormat = "%1scm || %2sfeet"

class DetailsFetchCharacterTransformerImpl @Inject constructor() :
    DetailsFetchCharacterTransformer {
    override fun toCharacterDetailsComponents(result: CharacterDetailsResponse): List<ComponentData> {
        val id = result.name
        val heading = TextComponentData(
            id = "details-Heading",
            text = "Basic Info",
            textStyle = TypographyStyle.TITLE_LARGE_DEFAULT
        )
        val element = ViewGroupComponentData(
            id = "basic",
            children = mutableListOf(
                ViewGroupComponentData(
                    id = "subviewgroup-$id",
                    children = mutableListOf(
                        TextComponentData(
                            id = "characterTitle--$id",
                            text = result.name,
                            textStyle = TypographyStyle.TITLE_MODERATE_DEMI_DEFAULT,
                            gravity = Gravity.START
                        ),
                        TextComponentData(
                            id = "characterBirthYear--$id",
                            text = result.birthYear,
                            textStyle = TypographyStyle.TITLE_MODERATE_DEMI_DEFAULT,
                            gravity = Gravity.START
                        ),
                        TextComponentData(
                            id = "characterBirthYear-$id",
                            text = String.format(HeightFormat, result.height, result.height),
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
        val heading2 = TextComponentData(
            id = "details-Heading2",
            text = "Species Info",
            textStyle = TypographyStyle.TITLE_LARGE_DEFAULT
        )
        val element2 = ViewGroupComponentData(
            id = "species",
            children = mutableListOf(EmptyComponentData("emptyvg")),
            width = Width.FILL,
            marginsHorizontal = 16,
            marginsVertical = 8,
            type = ViewGroupTypes.CARD
        )
        val heading3 = TextComponentData(
            id = "details-Heading3",
            text = "Planet Info",
            textStyle = TypographyStyle.TITLE_LARGE_DEFAULT
        )
        val element3 = ViewGroupComponentData(
            id = "planets",
            children = mutableListOf(EmptyComponentData("emptyvg")),
            width = Width.FILL,
            marginsHorizontal = 16,
            marginsVertical = 8,
            type = ViewGroupTypes.CARD
        )
        val heading4 = TextComponentData(
            id = "details-Heading4",
            text = "Films Info",
            textStyle = TypographyStyle.TITLE_LARGE_DEFAULT
        )
        val element4 = CarouselComponentData(
            id = "films",
            children = mutableListOf(EmptyComponentData("emptyc")),
            width = Width.FILL,
            paddingHorizontal = 8,
        )
        return listOf(heading, element, heading2, element2, heading3, element3, heading4, element4)
    }
}
