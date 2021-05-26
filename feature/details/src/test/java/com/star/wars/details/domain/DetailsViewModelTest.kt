package com.star.wars.details.domain

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.common.HttpUrl
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.willReturn
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest : BaseJUnitTest() {
    private lateinit var vm: DetailsViewModel

    private val fetchCharacterUseCase: DetailsFetchCharacterUseCase = mock()
    private val fetchPlanetUseCase: DetailsFetchPlanetUseCase = mock()
    private val fetchFilmsUseCase: DetailsFetchFilmsUseCase = mock()
    private val fetchSpeciesUseCase: DetailsFetchSpeciesUseCase = mock()
    private val observer: Observer<DetailsState> = mock()

    private val testUrl: HttpUrl = "test"
    private val urlsCombined: List<HttpUrl> = listOf(testUrl)
    private val successComponentList =
        mutableListOf<ComponentData>(TextComponentData(id = "testId"))
    private val emptyComponentList = mutableListOf<ComponentData>()
    private lateinit var dummyError: Throwable

    override fun start() {
        vm = DetailsViewModel(
            fetchCharacterUseCase,
            fetchPlanetUseCase,
            fetchFilmsUseCase,
            fetchSpeciesUseCase
        )
        dummyError = Throwable("mock")
    }

    override fun stop() {
        verifyNoMoreInteractions(
            fetchCharacterUseCase,
            fetchFilmsUseCase,
            fetchPlanetUseCase,
            fetchSpeciesUseCase
        )
    }

    @Test
    fun `when character details is fetched verify state when response is a success and has results`() {
        fetchCharacterUseCase.fetch(testUrl) willReturn Single.fromCallable { successComponentList }
        vm.state.observeForever(observer)

        vm.fetchDetails(testUrl)

        verify(observer).onChanged(DetailsState.ShowLoading)
        verify(fetchCharacterUseCase).fetch(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdateCharacterDetails(successComponentList))
    }

    @Test
    fun `when character details is fetched verify state when response is a success and has no results`() {
        fetchCharacterUseCase.fetch(testUrl) willReturn Single.fromCallable { emptyComponentList }
        vm.state.observeForever(observer)

        vm.fetchDetails(testUrl)

        verify(observer).onChanged(DetailsState.ShowLoading)
        verify(fetchCharacterUseCase).fetch(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdateCharacterDetails(emptyComponentList))
    }

    @Test
    fun `when character details is fetched verify state when response is a failure`() {
        fetchCharacterUseCase.fetch(testUrl) willReturn Single.error<Throwable>(dummyError)
        vm.state.observeForever(observer)

        vm.fetchDetails(testUrl)

        verify(observer).onChanged(DetailsState.ShowLoading)
        verify(fetchCharacterUseCase).fetch(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.Error("mock"))
    }

    @Test
    fun `when film details is fetched verify state when response is a success and has results`() {
        fetchFilmsUseCase.fetchCombined(urlsCombined) willReturn Single.fromCallable { successComponentList }
        vm.state.observeForever(observer)

        vm.fetchFilms(urlsCombined)

        verify(fetchFilmsUseCase).fetchCombined(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdateFilmsDetails(successComponentList))
    }

    @Test
    fun `when film details is fetched verify state when response is a success and has no results`() {
        fetchFilmsUseCase.fetchCombined(urlsCombined) willReturn Single.fromCallable { emptyComponentList }
        vm.state.observeForever(observer)

        vm.fetchFilms(urlsCombined)

        verify(fetchFilmsUseCase).fetchCombined(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdateFilmsDetails(emptyComponentList))
    }

    @Test
    fun `when film details is fetched verify state when response is a failure`() {
        fetchFilmsUseCase.fetchCombined(urlsCombined) willReturn Single.error<Throwable>(dummyError)
        vm.state.observeForever(observer)

        vm.fetchFilms(urlsCombined)

        verify(fetchFilmsUseCase).fetchCombined(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.Error("mock"))
    }

    @Test
    fun `when species details is fetched verify state when response is a success and has results`() {
        fetchSpeciesUseCase.fetchCombined(urlsCombined) willReturn Single.fromCallable { successComponentList }
        vm.state.observeForever(observer)

        vm.fetchSpecies(urlsCombined)

        verify(fetchSpeciesUseCase).fetchCombined(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdateSpeciesDetails(successComponentList))
    }

    @Test
    fun `when species details is fetched verify state when response is a success and has no results`() {
        fetchSpeciesUseCase.fetchCombined(urlsCombined) willReturn Single.fromCallable { emptyComponentList }
        vm.state.observeForever(observer)

        vm.fetchSpecies(urlsCombined)

        verify(fetchSpeciesUseCase).fetchCombined(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdateSpeciesDetails(emptyComponentList))
    }

    @Test
    fun `when species details is fetched verify state when response is a failure`() {
        fetchSpeciesUseCase.fetchCombined(urlsCombined) willReturn Single.error<Throwable>(dummyError)
        vm.state.observeForever(observer)

        vm.fetchSpecies(urlsCombined)

        verify(fetchSpeciesUseCase).fetchCombined(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.Error("mock"))
    }

    @Test
    fun `when planet details is fetched verify state when response is a success and has results`() {
        fetchPlanetUseCase.fetch(testUrl) willReturn Single.fromCallable { successComponentList }
        vm.state.observeForever(observer)

        vm.fetchPlanet(testUrl)

        verify(fetchPlanetUseCase).fetch(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdatePlanetDetails(successComponentList))
    }

    @Test
    fun `when planet details is fetched verify state when response is a success and has no results`() {
        fetchPlanetUseCase.fetch(testUrl) willReturn Single.fromCallable { emptyComponentList }
        vm.state.observeForever(observer)

        vm.fetchPlanet(testUrl)

        verify(fetchPlanetUseCase).fetch(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.UpdatePlanetDetails(emptyComponentList))
    }

    @Test
    fun `when planet details is fetched verify state when response is a failure`() {
        fetchPlanetUseCase.fetch(testUrl) willReturn Single.error<Throwable>(dummyError)
        vm.state.observeForever(observer)

        vm.fetchPlanet(testUrl)

        verify(fetchPlanetUseCase).fetch(any())
        verify(observer).onChanged(DetailsState.HideLoading)
        verify(observer).onChanged(DetailsState.Error("mock"))
    }
}
