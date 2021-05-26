package com.star.wars.details.domain

import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.common.base.ViewState

sealed class DetailsState : ViewState {
    data class UpdateCharacterDetails(val data: List<ComponentData>) : DetailsState()
    data class UpdatePlanetDetails(val data: List<ComponentData>) : DetailsState()
    data class UpdateSpeciesDetails(val data: List<ComponentData>) : DetailsState()
    data class UpdateFilmsDetails(val data: List<ComponentData>) : DetailsState()
    data class Error(val errorMessage: String) : DetailsState()
    object ShowLoading : DetailsState()
    object HideLoading : DetailsState()
}
