package com.star.wars.details.domain

import com.star.wars.common.HttpUrl
import com.star.wars.details.model.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface DetailsRepository {
    fun fetchCharacterDetail(urlToLoad: HttpUrl): Single<CharacterDetailsResponse>
    fun fetchPlanet(urlToLoad: HttpUrl): Single<PlanetsResponse>
    fun fetchSpecies(urls: List<HttpUrl>): Single<DetailsSpeciesCombinedResult>
    fun fetchFilms(urls: List<HttpUrl>): Single<DetailsFilmsCombinedResult>
}

class DetailsRepositoryImpl @Inject constructor(
    private val api: DetailsApi
) : DetailsRepository {
    override fun fetchCharacterDetail(urlToLoad: HttpUrl): Single<CharacterDetailsResponse> {
        return api.fetchCharacterDetails(urlToLoad)
    }

    override fun fetchPlanet(urlToLoad: HttpUrl): Single<PlanetsResponse> {
        return api.fetchPlanet(urlToLoad)
    }

    override fun fetchSpecies(urls: List<HttpUrl>): Single<DetailsSpeciesCombinedResult> {
        TODO()
    }

    override fun fetchFilms(urls: List<HttpUrl>): Single<DetailsFilmsCombinedResult> {
        return Observable.fromIterable(urls)
            .flatMapSingle {
                api.fetchFilm(it)
                    .subscribeOn(Schedulers.io())
            }
            .toList()
            .map {
                DetailsFilmsCombinedResult(it)
            }
    }
}
