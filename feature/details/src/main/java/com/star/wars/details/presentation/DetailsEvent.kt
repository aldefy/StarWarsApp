package com.star.wars.details.presentation

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.HttpUrl

sealed class DetailsEvent {
    data class DetailsErrorEvent(val message: String) : DetailsEvent()
    data class DetailsResultsFetched(val tabs: List<ComponentData>) : DetailsEvent()
    object CloseScreen : DetailsEvent()
    object ShowThemeChooserEvent : DetailsEvent()
    data class FetchPlanetEvent(val url:HttpUrl) : DetailsEvent()
    data class FetchSpeciesEvent(val urls: List<HttpUrl>) : DetailsEvent()
    data class FetchFilmsEvent(val urls: List<HttpUrl>) : DetailsEvent()
}
