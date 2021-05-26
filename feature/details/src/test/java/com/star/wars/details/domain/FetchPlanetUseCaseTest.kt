package com.star.wars.details.domain

import com.google.gson.JsonObject
import com.nhaarman.mockito_kotlin.*
import com.star.wars.andromeda.views.list.BaseComponentData
import com.star.wars.andromeda.views.list.internal.component.empty.data.EmptyComponentData
import com.star.wars.common.HttpUrl
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.common.test.willReturn
import com.star.wars.details.model.PlanetsResponse
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchPlanetUseCaseTest : BaseJUnitTest() {
    private lateinit var useCase: DetailsFetchPlanetUseCase

    private val repository: DetailsRepository = mock()
    private val mapper: DetailsFetchPlanetTransformer = mock()
    private lateinit var testThrowable: Throwable
    private val testUrl: HttpUrl = "test"
    private val planetResponse by lazy {
        load(
            PlanetsResponse::class.java,
            "planet_success_response.json"
        )
    }
    private val noDetailsResponse by lazy {
        load(
            JsonObject::class.java,
            "no_detail_response.json"
        )
    }
    private val componentDataResults = mutableListOf<BaseComponentData>(EmptyComponentData("test"))

    override fun start() {
        useCase = DetailsFetchPlanetInteractor(
            repository = repository,
            mapper = mapper
        )
        testThrowable = Throwable(message = "mock")
    }

    override fun stop() {
        verifyNoMoreInteractions(repository, mapper)
    }

    @Test
    fun `should emit list of component data when api fetch is a success with results`() {
        repository.fetchPlanet(testUrl) willReturn planetResponse
        mapper.toPlanetDetailsComponents(planetResponse) willReturn componentDataResults

        useCase.fetch(testUrl)
            .test()
            .assertValueCount(1)
            .assertComplete()
            .assertNoErrors()

        val inOrder = inOrder(repository, mapper)
        inOrder.verify(repository).fetchPlanet(any())
        inOrder.verify(mapper).toPlanetDetailsComponents(any())
    }

    @Test
    fun `should emit zero component data when api fetch is a success with no results`() {
        repository.fetchPlanet(testUrl) willReturn Single.fromCallable { noDetailsResponse }

        useCase.fetch(testUrl)
            .test()
            .assertError(Throwable::class.java)

        verify(repository).fetchPlanet(any())
    }
}
