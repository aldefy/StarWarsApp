package com.star.wars.details.domain

import androidx.lifecycle.MutableLiveData
import com.star.wars.common.addTo
import com.star.wars.common.base.BaseViewModel
import com.star.wars.common.singleIo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: DetailsUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<DetailsState>()
    val state = _state

    fun fetchDetails(url: String) {
        _state.value = DetailsState.ShowLoading
        useCase.fetchCharacterDetail(url)
            .compose(singleIo())
            .subscribe(
                { data ->
                    _state.value = DetailsState.UpdateData(data)
                    _state.value = DetailsState.HideLoading
                },
                { error ->
                    _state.value = DetailsState.Error(
                        error.message ?: "Something went wrong, Please try again!"
                    )
                    _state.value = DetailsState.HideLoading
                    error.printStackTrace()
                }
            )
            .addTo(bag)
    }
}
