package com.star.wars.details.domain

import androidx.lifecycle.MutableLiveData
import com.star.wars.common.HttpUrl
import com.star.wars.common.addTo
import com.star.wars.common.base.BaseViewModel
import com.star.wars.common.singleIo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val fetchCharacterUseCase: DetailsFetchCharacterUseCase,
    private val fetchPlanetUseCase: DetailsFetchPlanetUseCase,
    private val fetchFilmsUseCase: DetailsFetchFilmsUseCase,
    private val fetchSpeciesUseCase: DetailsFetchSpeciesUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<DetailsState>()
    val state = _state

    fun fetchDetails(url: String) {
        _state.value = DetailsState.ShowLoading
        fetchCharacterUseCase.fetch(url)
            .compose(singleIo())
            .subscribe(
                { content ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.UpdateCharacterDetails(content)
                },
                { error ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.Error(
                        error.message ?: "Something went wrong, Please try again!"
                    )
                    error.printStackTrace()
                }
            )
            .addTo(bag)
    }

    fun fetchFilms(urls: List<HttpUrl>) {
        fetchFilmsUseCase.fetchCombined(urls)
            .compose(singleIo())
            .subscribe(
                { content ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.UpdateFilmsDetails(content)
                },
                { error ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.Error(
                        error.message ?: "Something went wrong, Please try again!"
                    )
                    error.printStackTrace()
                }
            )
            .addTo(bag)
    }

    fun fetchPlanet(url: HttpUrl) {
        fetchPlanetUseCase.fetch(url)
            .compose(singleIo())
            .subscribe(
                { content ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.UpdatePlanetDetails(content)
                },
                { error ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.Error(
                        error.message ?: "Something went wrong, Please try again!"
                    )
                    error.printStackTrace()
                }
            )
            .addTo(bag)
    }

    fun fetchSpecies(urls: List<HttpUrl>) {
        fetchSpeciesUseCase.fetchCombined(urls)
            .compose(singleIo())
            .subscribe(
                { content ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.UpdateSpeciesDetails(content)
                },
                { error ->
                    _state.value = DetailsState.HideLoading
                    _state.value = DetailsState.Error(
                        error.message ?: "Something went wrong, Please try again!"
                    )
                    error.printStackTrace()
                }
            )
            .addTo(bag)
    }

}
