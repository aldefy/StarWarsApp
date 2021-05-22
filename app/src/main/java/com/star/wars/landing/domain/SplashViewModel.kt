package com.star.wars.landing.domain

import androidx.lifecycle.MutableLiveData
import com.star.wars.common.addTo
import com.star.wars.common.base.BaseViewModel
import com.star.wars.common.observableIo
import com.star.wars.landing.presentation.SplashState
import com.star.wars.landing.presentation.SplashState.NavigateToNextScreen
import com.star.wars.landing.presentation.SplashState.ShowLoading
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashViewModel: BaseViewModel() {
    private val _state = MutableLiveData<SplashState>()
    val state = _state
    private val splashTimer = 2L

    fun load() {
        _state.value = ShowLoading
    }

    fun startTimer() {
        Observable.timer(splashTimer, TimeUnit.SECONDS)
            .compose(observableIo())
            .subscribe {
                _state.value = NavigateToNextScreen
            }
            .addTo(bag)
    }
}
