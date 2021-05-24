package com.star.wars.search.presentation

import android.os.Parcelable
import com.star.wars.andromeda.views.list.ComponentData

sealed class SearchEvent {
    data class SearchTriggeredEvent(val searchText: String) : SearchEvent()
    data class SearchErrorEvent(val message: String) : SearchEvent()
    data class SearchResultsFetched(val tabs: List<ComponentData>) : SearchEvent()
    object SearchCleared : SearchEvent()
    data class ClickFiredEvent(val extraPayload: Parcelable) : SearchEvent()
    data class ComponentNotDrawnEvent(val componentId: String): SearchEvent()
    object CloseScreen : SearchEvent()
    object ShowThemeChooserEvent : SearchEvent()
}
