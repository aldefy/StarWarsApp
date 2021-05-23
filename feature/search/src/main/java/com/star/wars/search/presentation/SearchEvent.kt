package com.star.wars.search.presentation

import com.star.wars.search.model.ResultsItem

sealed class SearchEvent {
    data class SearchErrorEvent(val message: String): SearchEvent()
    data class SearchResultsFetched(val tabs: List<ResultsItem>): SearchEvent()
}
