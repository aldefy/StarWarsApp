package com.star.wars.search.domain

import com.star.wars.search.model.SearchApi
import com.star.wars.search.model.SearchPeopleResponse
import io.reactivex.Single
import javax.inject.Inject

interface SearchRepository {
    fun searchCharacter(query: String): Single<SearchPeopleResponse>
}

class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApi
) : SearchRepository {
    override fun searchCharacter(query: String): Single<SearchPeopleResponse> {
        return api.searchCharacter(query)
    }
}
