package com.star.wars.details.domain

import com.nhaarman.mockito_kotlin.*
import com.star.wars.andromeda.views.list.BaseComponentData
import com.star.wars.andromeda.views.list.internal.component.empty.data.EmptyComponentData
import com.star.wars.common.HttpUrl
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.common.test.willReturn
import com.star.wars.details.model.DetailsFilmsCombinedResult
import com.star.wars.details.model.FilmsResponse
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchFilmsUseCaseTest : BaseJUnitTest() {
    private lateinit var useCase: DetailsFetchFilmsUseCase

    private val repository: DetailsRepository = mock()
    private val mapper: DetailsFetchFilmsTransformer = mock()
    private lateinit var testThrowable: Throwable
    private val testUrl: HttpUrl = "test"
    private val urlsCombined: List<HttpUrl> = listOf(testUrl)
    private val filmsResponse by lazy {
        load(
            FilmsResponse::class.java,
            "films_success_response.json"
        )
    }
    private val componentDataResults = mutableListOf<BaseComponentData>(EmptyComponentData("test"))

    override fun start() {
        useCase = DetailsFetchFilmsInteractor(
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
        repository.fetchFilms(urlsCombined) willReturn Single.fromCallable {
            DetailsFilmsCombinedResult(listOf(filmsResponse))
        }
        mapper.toFilmsDetailsComponents(DetailsFilmsCombinedResult(listOf(filmsResponse))) willReturn componentDataResults

        useCase.fetchCombined(urlsCombined)
            .test()
            .assertValueCount(1)
            .assertComplete()
            .assertNoErrors()

        val inOrder = inOrder(repository, mapper)
        inOrder.verify(repository).fetchFilms(any())
        inOrder.verify(mapper).toFilmsDetailsComponents(any())
    }

    @Test
    fun `should emit zero component data when api fetch is a success with no results`() {
        repository.fetchFilms(urlsCombined) willReturn Single.error(testThrowable)

        useCase.fetchCombined(urlsCombined)
            .test()
            .assertError(Throwable::class.java)

        verify(repository).fetchFilms(any())
    }
}
