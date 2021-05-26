package com.star.wars.details.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface DetailsApi {
    @GET
    fun fetchCharacterDetails(@Url characterUrl: String): Single<CharacterDetailsResponse>

    @GET
    fun fetchPlanet(@Url planetUrl: String): Single<PlanetsResponse>

    @GET
    fun fetchSpecie(@Url specieUrl: String): Single<SpeciesResponse>

    @GET
    fun fetchFilm(@Url filmUrl: String): Single<FilmsResponse>
}
