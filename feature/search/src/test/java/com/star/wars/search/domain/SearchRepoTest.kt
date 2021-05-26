package com.star.wars.search.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.common.test.willReturn
import com.star.wars.search.model.SearchApi
import com.star.wars.search.model.SearchPeopleResponse
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchRepoTest : BaseJUnitTest() {
    private lateinit var repo: SearchRepository
    private val query = "test"

    private val api: SearchApi = mock()
    private val searchResultsResponse by lazy {
        load(
            SearchPeopleResponse::class.java,
            "search_results_success_response.json"
        )
    }

    override fun start() {
        repo = SearchRepositoryImpl(
            api = api,
        )
    }

    override fun stop() {
        verifyNoMoreInteractions(api)
    }

    @Test
    fun `when search character is triggered assert that api is called`() {
        api.searchCharacter(query) willReturn Single.fromCallable { searchResultsResponse }

        repo.searchCharacter(query)

        verify(api).searchCharacter(any())
    }

    @Test
    fun `when search character is triggered verify response as a success`() {
        api.searchCharacter(query) willReturn Single.fromCallable { searchResultsResponse }

        repo.searchCharacter(query)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertOf {
                assertEquals(searchResultsResponse, it.values().first())
            }

        verify(api).searchCharacter(any())
    }
}
