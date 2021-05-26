package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.HttpUrl
import com.star.wars.common.HttpsTransformer
import io.reactivex.Single
import javax.inject.Inject

interface DetailsFetchFilmsUseCase : DetailsCombinedFetchUseCase

class DetailsFetchFilmsInteractor @Inject constructor(
    private val mapper: DetailsFetchFilmsTransformer,
    private val repository: DetailsRepository
) : DetailsFetchFilmsUseCase {
    override fun fetchCombined(urls: List<HttpUrl>): Single<List<ComponentData>> {
        return repository.fetchFilms(urls)
            .map { response ->
                mapper.toFilmsDetailsComponents(response)
            }
    }
}
