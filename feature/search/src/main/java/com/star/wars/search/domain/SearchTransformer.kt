package com.star.wars.search.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.Gravity
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.Orientation
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupTypes
import com.star.wars.andromeda.views.text.TypographyStyle
import com.star.wars.search.model.CharacterResultItem
import com.star.wars.search.model.SearchEmptyError
import javax.inject.Inject

interface SearchTransformer {
    fun results(resultItems: List<CharacterResultItem>): List<ComponentData>
}

class SearchTransformerImpl @Inject constructor() : SearchTransformer {
    @Throws(SearchEmptyError::class)
    override fun results(resultItems: List<CharacterResultItem>): List<ComponentData> {
        if (resultItems.isEmpty())
            throw SearchEmptyError()
        return resultItems
            .map {
                toComponentData(result = it)
            }
    }

    private fun toComponentData(result: CharacterResultItem): ComponentData {
        val id = result.url
        return ViewGroupComponentData(
            id = id,
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
                            id = "characterBirthYear-$id",
                            text = result.birthYear,
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
            type = ViewGroupTypes.CARD,
            extraPayload = result
        )
    }

}
