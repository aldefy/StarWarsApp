package com.star.wars.details.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.star.wars.common.HttpUrl
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.load
import com.star.wars.common.test.willReturn
import com.star.wars.details.model.*
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailsRepositoryTest : BaseJUnitTest() {
    private lateinit var repo: DetailsRepository

    private val api: DetailsApi = mock()
    private val testUrl: HttpUrl = "test"
    private val urlsCombined: List<HttpUrl> = listOf(testUrl)
    private val characterDetailsResponse by lazy {
        load(
            CharacterDetailsResponse::class.java,
            "character_details_success_response.json"
        )
    }
    private val planetResponse by lazy {
        load(
            PlanetsResponse::class.java,
            "planet_success_response.json"
        )
    }
    private val speciesResponse by lazy {
        load(
            SpeciesResponse::class.java,
            "species_success_response.json"
        )
    }
    private val filmsResponse by lazy {
        load(
            FilmsResponse::class.java,
            "films_success_response.json"
        )
    }
    private lateinit var dummyError: Throwable

    override fun start() {
        repo = DetailsRepositoryImpl(
            api
        )
        dummyError = Throwable("mock")
    }

    override fun stop() {
        verifyNoMoreInteractions(api)
    }

    @Test
    fun `when character details is fetched assert that api is called`() {
        api.fetchCharacterDetails(testUrl) willReturn Single.fromCallable { characterDetailsResponse }

        repo.fetchCharacterDetail(testUrl)

        verify(api).fetchCharacterDetails(any())
    }

    @Test
    fun `when character details is fetched verify response as a success`() {
        api.fetchCharacterDetails(testUrl) willReturn Single.fromCallable { characterDetailsResponse }

        repo.fetchCharacterDetail(testUrl)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertOf {
                Assert.assertEquals(characterDetailsResponse, it.values().first())
            }

        verify(api).fetchCharacterDetails(any())
    }

    @Test
    fun `when character details is fetched verify response as a failure`() {
        api.fetchCharacterDetails(testUrl) willReturn Single.error(dummyError)

        repo.fetchCharacterDetail(testUrl)
            .test()
            .assertNotComplete()
            .assertError(dummyError)

        verify(api).fetchCharacterDetails(any())
    }

    @Test
    fun `when planet details is fetched assert that api is called`() {
        api.fetchPlanet(testUrl) willReturn Single.fromCallable { planetResponse }

        repo.fetchPlanet(testUrl)

        verify(api).fetchPlanet(any())
    }

    @Test
    fun `when planet details is fetched verify response as a success`() {
        api.fetchPlanet(testUrl) willReturn Single.fromCallable { planetResponse }

        repo.fetchPlanet(testUrl)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertOf {
                Assert.assertEquals(planetResponse, it.values().first())
            }

        verify(api).fetchPlanet(any())
    }

    @Test
    fun `when planet details is fetched verify response as a failure`() {
        api.fetchPlanet(testUrl) willReturn Single.error(dummyError)

        repo.fetchPlanet(testUrl)
            .test()
            .assertNotComplete()
            .assertError(dummyError)

        verify(api).fetchPlanet(any())
    }

    @Test
    fun `when species details is fetched assert that api is called`() {
        api.fetchSpecie(testUrl) willReturn Single.fromCallable { speciesResponse }

        repo.fetchSpecies(urlsCombined)
            .test()

        verify(api).fetchSpecie(any())
    }

    @Test
    fun `when species details is fetched verify response as a success`() {
        api.fetchSpecie(testUrl) willReturn Single.fromCallable { speciesResponse }
        val combinedResult = DetailsSpeciesCombinedResult(
            listOf(speciesResponse)
        )

        repo.fetchSpecies(urlsCombined)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertOf {
                Assert.assertEquals(combinedResult, it.values().first())
            }

        verify(api).fetchSpecie(any())
    }

    @Test
    fun `when species details is fetched verify response as a failure`() {
        api.fetchSpecie(testUrl) willReturn Single.error(dummyError)

        repo.fetchSpecies(urlsCombined)
            .test()
            .assertNotComplete()
            .assertError(dummyError)

        verify(api).fetchSpecie(any())
    }

    @Test
    fun `when films details is fetched assert that api is called`() {
        api.fetchFilm(testUrl) willReturn Single.fromCallable { filmsResponse }

        repo.fetchFilms(urlsCombined)
            .test()

        verify(api).fetchFilm(any())
    }

    @Test
    fun `when films details is fetched verify response as a success`() {
        api.fetchFilm(testUrl) willReturn Single.fromCallable { filmsResponse }
        val combinedResult = DetailsFilmsCombinedResult(
            listOf(filmsResponse)
        )

        repo.fetchFilms(urlsCombined)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertOf {
                Assert.assertEquals(combinedResult, it.values().first())
            }

        verify(api).fetchFilm(any())
    }

    @Test
    fun `when films details is fetched verify response as a failure`() {
        api.fetchFilm(testUrl) willReturn Single.error(dummyError)

        repo.fetchFilms(urlsCombined)
            .test()
            .assertNotComplete()
            .assertError(dummyError)

        verify(api).fetchFilm(any())
    }

}
