package com.star.wars.search.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.HttpsTransformer
import io.reactivex.Single
import javax.inject.Inject

interface SearchUseCase {
    fun searchCharacter(query: String): Single<List<ComponentData>>
}

class SearchInteractor @Inject constructor(
    private val httpsTransformer: HttpsTransformer,
    private val mapper: SearchTransformer,
    private val repository: SearchRepository
) : SearchUseCase {
    override fun searchCharacter(query: String): Single<List<ComponentData>> {
        return repository.searchCharacter(query)
            .map {
                it.results.map { item ->
                    item.url = httpsTransformer.toHttpUrl(item.url)
                }
                it.results
            }
            .map { mapper.results(it) }
    }
}
