package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import io.reactivex.Single
import javax.inject.Inject

interface DetailsUseCase {
    fun fetchCharacterDetail(urlToLoad: String): Single<List<ComponentData>>
    fun fetchPlanet(urlToLoad: String): Single<List<ComponentData>>
    fun fetchSpecies(urlToLoad: String): Single<List<ComponentData>>
    fun fetchFilms(urlToLoad: String): Single<List<ComponentData>>
}

class DetailsInteractor @Inject constructor(
    private val mapper: DetailsTransformer,
    private val repository: DetailsRepository
) : DetailsUseCase {
    override fun fetchCharacterDetail(urlToLoad: String): Single<List<ComponentData>> {
        TODO()
    }

    override fun fetchPlanet(urlToLoad: String): Single<List<ComponentData>> {
        TODO()
    }

    override fun fetchSpecies(urlToLoad: String): Single<List<ComponentData>> {
        TODO()
    }

    override fun fetchFilms(urlToLoad: String): Single<List<ComponentData>> {
        TODO()
    }
}
