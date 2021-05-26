package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.details.model.CharacterDetailsResponse
import com.star.wars.details.model.PlanetsResponse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchPlanetTransformerTest : BaseJUnitTest() {

    private lateinit var transformer: DetailsFetchPlanetTransformer
    private val planetResponse by lazy {
        load(
            PlanetsResponse::class.java,
            "planet_success_response.json"
        )
    }

    override fun start() {
        transformer = DetailsFetchPlanetTransformerImpl()
    }

    override fun stop() {
    }

    @Test
    fun `given response then return list of ComponentData`() {
        val transformedResult = transformer.toPlanetDetailsComponents(
            planetResponse
        )
        assertTrue(
            transformedResult.size == 1
        )
    }

    @Test
    fun `given response then verify return list of ComponentData contains ViewGroup`() {
        val transformedResult = transformer.toPlanetDetailsComponents(
            planetResponse
        )
        assertTrue(
            transformedResult[0] is ViewGroupComponentData
        )
    }

}
