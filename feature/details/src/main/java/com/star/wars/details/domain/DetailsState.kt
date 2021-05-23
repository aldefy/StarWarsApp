package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.base.ViewState

sealed class DetailsState : ViewState {
    data class UpdateData(val data: List<ComponentData>) : DetailsState()
    data class Error(val errorMessage: String) : DetailsState()
    object ShowLoading : DetailsState()
    object HideLoading : DetailsState()
}
