package com.star.wars.search.domain

import com.star.wars.andromeda.views.list.ComponentData
import io.reactivex.Single
import javax.inject.Inject

interface SearchUseCase {
    fun searchCharacter(query: String): Single<List<ComponentData>>
}

class SearchInteractor @Inject constructor(
    private val mapper: SearchTransformer,
    private val repository: SearchRepository
) : SearchUseCase {
    override fun searchCharacter(query: String): Single<List<ComponentData>> {
        return repository.searchCharacter(query)
            .map { mapper.results(it.results) }
    }
}
