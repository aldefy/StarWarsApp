package com.star.wars.search.domain

import androidx.lifecycle.MutableLiveData
import com.star.wars.common.addTo
import com.star.wars.common.base.BaseViewModel
import com.star.wars.common.singleIo
import com.star.wars.search.domain.SearchState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<SearchState>()
    val state = _state

    fun searchCharacter(query: String) {
        _state.value = ShowLoading
        useCase.searchCharacter(query)
            .compose(singleIo())
            .subscribe(
                { data ->
                    _state.value = LoadData(data)
                    _state.value = HideLoading
                },
                { error ->
                    _state.value = Error(
                        error.message ?: "Something went wrong, Please try again!"
                    )
                    _state.value = HideLoading
                    error.printStackTrace()
                }
            )
            .addTo(bag)
    }
}
