package com.star.wars.search.domain

import com.star.wars.search.model.ResultsItem
import io.reactivex.Single
import javax.inject.Inject

interface SearchUseCase {
    fun searchCharacter(query: String): Single<List<ResultsItem>>
}

class SearchInteractor @Inject constructor(
    private val repository: SearchRepository
) : SearchUseCase {
    override fun searchCharacter(query: String): Single<List<ResultsItem>> {
        return repository.searchCharacter(query)
            .map {
                it.results
            }
    }
}
