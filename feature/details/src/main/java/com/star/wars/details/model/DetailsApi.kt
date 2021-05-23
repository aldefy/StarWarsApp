package com.star.wars.details.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface DetailsApi {
    @GET
    fun fetchCharacterDetails(@Url characterUrl: String): Single<Any>

    @GET
    fun fetchPlanet(@Url planetUrl: String): Single<Any>

    @GET
    fun fetchSpecies(@Url speciesUrl: String): Single<Any>

    @GET
    fun fetchFilms(@Url filmsUrl: String): Single<Any>
}
