package com.star.wars.search.domain

import com.nhaarman.mockito_kotlin.*
import com.star.wars.andromeda.views.list.BaseComponentData
import com.star.wars.andromeda.views.list.internal.component.empty.data.EmptyComponentData
import com.star.wars.common.HttpsTransformer
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.common.test.willReturn
import com.star.wars.search.model.SearchPeopleResponse
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchUseCaseTest : BaseJUnitTest() {
    private lateinit var useCase: SearchUseCase

    private val repository: SearchRepository = mock()
    private val httpsTransformer: HttpsTransformer = mock()
    private val mapper: SearchTransformer = mock()
    private val query = "test"
    private lateinit var testThrowable: Throwable
    private val searchResultsResponse by lazy {
        load(
            SearchPeopleResponse::class.java,
            "search_results_success_response.json"
        )
    }
    private val searchNoResultsResponse by lazy {
        load(
            SearchPeopleResponse::class.java,
            "search_no_results_success_response.json"
        )
    }

    private val componentDataResults = mutableListOf<BaseComponentData>(EmptyComponentData("test"))

    override fun start() {
        useCase = SearchInteractor(
            repository = repository,
            httpsTransformer = httpsTransformer,
            mapper = mapper
        )
        testThrowable = Throwable(message = "mock")
    }

    override fun stop() {
        verifyNoMoreInteractions(repository, httpsTransformer, mapper)
    }

    @Test
    fun `should emit list of component data when api fetch is a success with results`() {
        repository.searchCharacter(query) willReturn searchResultsResponse
        val results = searchResultsResponse.results
        httpsTransformer.toHttpUrl(ArgumentMatchers.anyString()) willReturn "https://"
        mapper.results(results) willReturn componentDataResults

        useCase.searchCharacter(query)
            .test()
            .assertValueCount(1)
            .assertComplete()
            .assertNoErrors()

        val inOrder = inOrder(repository, httpsTransformer, mapper)
        inOrder.verify(repository).searchCharacter(any())
        inOrder.verify(httpsTransformer, times(results.size)).toHttpUrl(any())
        inOrder.verify(mapper).results(any())
    }

    @Test
    fun `should emit zero component data when api fetch is a success with no results`() {
        repository.searchCharacter(query) willReturn searchNoResultsResponse
        val results = searchNoResultsResponse.results
        mapper.results(results) willReturn componentDataResults

        useCase.searchCharacter(query)
            .test()
            .assertValueCount(1)
            .assertComplete()
            .assertNoErrors()

        val inOrder = inOrder(repository, httpsTransformer, mapper)
        inOrder.verify(repository).searchCharacter(any())
        inOrder.verify(httpsTransformer, never()).toHttpUrl(any())
        inOrder.verify(mapper).results(any())
    }

    @Test
    fun `should emit error when api fetch is a failure`() {
        repository.searchCharacter(query) willReturn Single.error(testThrowable)

        useCase.searchCharacter(query)
            .test()
            .assertError(Throwable::class.java)
            .assertNoValues()
            .assertNotComplete()

        verify(repository).searchCharacter(any())
    }
}
