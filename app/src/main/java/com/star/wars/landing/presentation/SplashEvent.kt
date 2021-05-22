package com.star.wars.landing.presentation

sealed class SplashEvent {
    object NavigateToNextScreenEvent: SplashEvent()
    object StartTimerEvent: SplashEvent()
}
