package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import javax.inject.Inject

interface DetailsTransformer {
    fun results(resultItems: List<Any>): List<ComponentData>
}

class DetailsTransformerImpl @Inject constructor() : DetailsTransformer {
    override fun results(resultItems: List<Any>): List<ComponentData> {
        return resultItems.map {
            toComponentData(result = it)
        }
    }

    private fun toComponentData(result: Any): ComponentData {
      TODO()
    }

}
