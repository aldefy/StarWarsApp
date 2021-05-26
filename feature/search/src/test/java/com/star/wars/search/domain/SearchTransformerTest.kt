package com.star.wars.search.domain

import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.search.model.CharacterResultItem
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchTransformerTest : BaseJUnitTest() {

    private lateinit var transformer: SearchTransformer

    override fun start() {
        transformer = SearchTransformerImpl()
    }

    override fun stop() {
    }

    @Test
    fun `given list of CharacterResult then return list of ComponentData`() {
        val resultItems = listOf(CharacterResultItem())
        val transformedResult = transformer.results(
            resultItems
        )
        assertTrue(
            transformedResult.size == 1
        )
    }

    @Test
    fun `given empty list of CharacterResult then return empty list of ComponentData`() {
        val resultItems = listOf<CharacterResultItem>()
        val transformedResult = transformer.results(
            resultItems
        )
        assertTrue(
            transformedResult.isEmpty()
        )
    }

    @Test
    fun `given list of CharacterResult then verify return list of ComponentData contains ViewGroup`() {
        val resultItems = listOf(CharacterResultItem())
        val transformedResult = transformer.results(
            resultItems
        )
        assertTrue(
            transformedResult[0] is ViewGroupComponentData
        )
    }


}
