package com.star.wars.details.presentation

import com.star.wars.andromeda.views.list.ComponentData

sealed class DetailsEvent {
    data class DetailsErrorEvent(val message: String) : DetailsEvent()
    data class DetailsResultsFetched(val tabs: List<ComponentData>) : DetailsEvent()
    object CloseScreen : DetailsEvent()
    object ShowThemeChooserEvent : DetailsEvent()
}
