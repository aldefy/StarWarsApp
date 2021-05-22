package com.star.wars.landing.presentation

import com.star.wars.common.base.ViewState

sealed class SplashState : ViewState {
    object ShowLoading: SplashState()
    object NavigateToNextScreen: SplashState()
}
