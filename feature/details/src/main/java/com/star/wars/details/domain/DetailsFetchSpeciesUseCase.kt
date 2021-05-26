package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.HttpUrl
import com.star.wars.common.HttpsTransformer
import io.reactivex.Single
import javax.inject.Inject

interface DetailsFetchSpeciesUseCase : DetailsCombinedFetchUseCase

class DetailsFetchSpeciesInteractor @Inject constructor(
    private val mapper: DetailsFetchSpeciesTransformer,
    private val repository: DetailsRepository
) : DetailsFetchSpeciesUseCase {
    override fun fetchCombined(urls: List<HttpUrl>): Single<List<ComponentData>> {
        return repository.fetchSpecies(urls)
            .map { response ->
                mapper.toSpeciesDetailsComponents(response)
            }
    }
}
