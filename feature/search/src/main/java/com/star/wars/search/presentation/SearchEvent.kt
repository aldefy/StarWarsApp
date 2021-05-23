package com.star.wars.search.presentation

import com.star.wars.andromeda.views.list.ComponentData

sealed class SearchEvent {
    data class SearchTriggeredEvent(val searchText: String) : SearchEvent()
    data class SearchErrorEvent(val message: String) : SearchEvent()
    data class SearchResultsFetched(val tabs: List<ComponentData>) : SearchEvent()
    object SearchCleared : SearchEvent()
}
