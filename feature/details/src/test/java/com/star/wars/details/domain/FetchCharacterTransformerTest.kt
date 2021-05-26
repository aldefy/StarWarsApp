package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.details.model.CharacterDetailsResponse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchCharacterTransformerTest : BaseJUnitTest() {

    private lateinit var transformer: DetailsFetchCharacterTransformer
    private val characterDetailsResponse by lazy {
        load(
            CharacterDetailsResponse::class.java,
            "character_details_success_response.json"
        )
    }

    override fun start() {
        transformer = DetailsFetchCharacterTransformerImpl()
    }

    override fun stop() {
    }

    @Test
    fun `given response then return list of ComponentData`() {
        val transformedResult = transformer.toCharacterDetailsComponents(
            characterDetailsResponse
        )
        assertTrue(
            transformedResult.size == 8
        )
    }

    @Test
    fun `given response then verify return list of ComponentData contains ViewGroup`() {
        val transformedResult = transformer.toCharacterDetailsComponents(
            characterDetailsResponse
        )
        assertTrue(
            transformedResult[1] is ViewGroupComponentData
        )
    }

}
