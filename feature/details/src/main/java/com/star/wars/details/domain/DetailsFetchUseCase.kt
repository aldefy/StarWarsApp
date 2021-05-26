package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.HttpUrl
import io.reactivex.Single

interface DetailsFetchUseCase {
    fun fetch(urlToLoad: HttpUrl): Single<List<ComponentData>>
}

interface DetailsCombinedFetchUseCase {
    fun fetchCombined(urls: List<HttpUrl>):Single<List<ComponentData>>
}
