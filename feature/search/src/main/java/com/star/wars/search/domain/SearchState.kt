package com.star.wars.search.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.base.ViewState

sealed class SearchState : ViewState {
    data class LoadData(val data: List<ComponentData>) : SearchState()
    data class Error(val errorMessage: String) : SearchState()
    object ShowLoading : SearchState()
    object HideLoading : SearchState()
}
