package com.star.wars.details.domain

import com.star.wars.details.model.DetailsApi
import io.reactivex.Single
import javax.inject.Inject

interface DetailsRepository {
    fun fetchCharacterDetail(urlToLoad: String): Single<Any>
    fun fetchPlanet(urlToLoad: String): Single<Any>
    fun fetchSpecies(urlToLoad: String): Single<Any>
    fun fetchFilms(urlToLoad: String): Single<Any>
}

class DetailsRepositoryImpl @Inject constructor(
    private val api: DetailsApi
) : DetailsRepository {
    override fun fetchCharacterDetail(urlToLoad: String): Single<Any> {
        return api.fetchCharacterDetails(urlToLoad)
    }

    override fun fetchPlanet(urlToLoad: String): Single<Any> {
        return api.fetchPlanet(urlToLoad)
    }

    override fun fetchSpecies(urlToLoad: String): Single<Any> {
        return api.fetchSpecies(urlToLoad)
    }

    override fun fetchFilms(urlToLoad: String): Single<Any> {
        return api.fetchFilms(urlToLoad)
    }
}
