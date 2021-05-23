package com.star.wars.search.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("people/")
    fun searchCharacter(@Query("search") name: String): Single<SearchPeopleResponse>
}
