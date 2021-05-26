package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.HttpsTransformer
import io.reactivex.Single
import javax.inject.Inject

interface DetailsFetchCharacterUseCase : DetailsFetchUseCase

class DetailsFetchCharacterInteractor @Inject constructor(
    private val httpsTransformer: HttpsTransformer,
    private val mapper: DetailsFetchCharacterTransformer,
    private val repository: DetailsRepository
) : DetailsFetchCharacterUseCase {
    override fun fetch(urlToLoad: String): Single<List<ComponentData>> {
        return repository.fetchCharacterDetail(urlToLoad)
            .map {
                it.films = it.films?.map { url ->
                    httpsTransformer.toHttpUrl(url)
                }
                it.vehicles = it.vehicles?.map { url ->
                    httpsTransformer.toHttpUrl(url)
                }
                it.starships = it.starships?.map { url ->
                    httpsTransformer.toHttpUrl(url)
                }
                it
            }
            .map { response ->
                mapper.toCharacterDetailsComponents(response)
            }
    }
}
