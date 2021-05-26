package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.HttpsTransformer
import io.reactivex.Single
import javax.inject.Inject

interface DetailsFetchPlanetUseCase : DetailsFetchUseCase

class DetailsFetchPlanetInteractor @Inject constructor(
    private val mapper: DetailsFetchPlanetTransformer,
    private val repository: DetailsRepository
) : DetailsFetchPlanetUseCase {
    override fun fetch(urlToLoad: String): Single<List<ComponentData>> {
        return repository.fetchPlanet(urlToLoad)
            .map { response ->
                mapper.toPlanetDetailsComponents(response)
            }
    }
}
