package com.star.wars.details.domain

import com.google.gson.JsonObject
import com.nhaarman.mockito_kotlin.*
import com.star.wars.andromeda.views.list.BaseComponentData
import com.star.wars.andromeda.views.list.internal.component.empty.data.EmptyComponentData
import com.star.wars.common.HttpUrl
import com.star.wars.common.HttpsTransformer
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.common.test.willReturn
import com.star.wars.details.model.CharacterDetailsResponse
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchCharacterUseCaseTest : BaseJUnitTest() {
    private lateinit var useCase: DetailsFetchCharacterUseCase

    private val repository: DetailsRepository = mock()
    private val httpsTransformer: HttpsTransformer = mock()
    private val mapper: DetailsFetchCharacterTransformer = mock()
    private lateinit var testThrowable: Throwable
    private val testUrl: HttpUrl = "test"
    private val characterDetailsResponse by lazy {
        load(
            CharacterDetailsResponse::class.java,
            "character_details_success_response.json"
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
        useCase = DetailsFetchCharacterInteractor(
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
        repository.fetchCharacterDetail(testUrl) willReturn characterDetailsResponse
        httpsTransformer.toHttpUrl(ArgumentMatchers.anyString()) willReturn "https://"
        mapper.toCharacterDetailsComponents(characterDetailsResponse) willReturn componentDataResults

        useCase.fetch(testUrl)
            .test()
            .assertValueCount(1)
            .assertComplete()
            .assertNoErrors()

        val inOrder = inOrder(repository, httpsTransformer, mapper)
        inOrder.verify(repository).fetchCharacterDetail(any())
        inOrder.verify(httpsTransformer, times(8)).toHttpUrl(any())
        inOrder.verify(mapper).toCharacterDetailsComponents(any())
    }

    @Test
    fun `should emit zero component data when api fetch is a success with no results`() {
        repository.fetchCharacterDetail(testUrl) willReturn Single.fromCallable { noDetailsResponse }

        useCase.fetch(testUrl)
            .test()
            .assertError(Throwable::class.java)

        verify(repository).fetchCharacterDetail(any())
    }
}
