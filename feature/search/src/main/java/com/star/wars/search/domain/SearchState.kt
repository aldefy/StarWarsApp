package com.star.wars.search.domain

import com.star.wars.common.base.ViewState
import com.star.wars.search.model.ResultsItem

sealed class SearchState : ViewState {
    data class LoadData(val data: List<ResultsItem>) : SearchState()
    data class Error(val errorMessage: String) : SearchState()
    object ShowLoading : SearchState()
    object HideLoading : SearchState()
}
