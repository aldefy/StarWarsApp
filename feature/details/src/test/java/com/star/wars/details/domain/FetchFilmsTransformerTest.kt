package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.internal.component.carousel.data.CarouselComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.details.model.DetailsFilmsCombinedResult
import com.star.wars.details.model.DetailsSpeciesCombinedResult
import com.star.wars.details.model.FilmsResponse
import com.star.wars.details.model.SpeciesResponse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchFilmsTransformerTest : BaseJUnitTest() {
    private lateinit var transformer: DetailsFetchFilmsTransformer
    private val filmsResponse by lazy {
        load(
            FilmsResponse::class.java,
            "films_success_response.json"
        )
    }
    private lateinit var detailsFilmsCombinedResult: DetailsFilmsCombinedResult

    override fun start() {
        transformer = DetailsFetchFilmsTransformerImpl()
        detailsFilmsCombinedResult = DetailsFilmsCombinedResult(listOf(filmsResponse))
    }

    override fun stop() {
    }

    @Test
    fun `given response then return list of ComponentData`() {
        val transformedResult = transformer.toFilmsDetailsComponents(
            detailsFilmsCombinedResult
        )
        assertTrue(
            transformedResult.size == 1
        )
    }

    @Test
    fun `given response then verify return list of ComponentData contains ViewGroup`() {
        val transformedResult = transformer.toFilmsDetailsComponents(
            detailsFilmsCombinedResult
        )
        assertTrue(
            transformedResult[0] is CarouselComponentData
        )
    }
}
