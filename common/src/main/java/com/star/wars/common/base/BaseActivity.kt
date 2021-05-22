package com.star.wars.common.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity<S : ViewState> : AppCompatActivity() {
    val _state = PublishSubject.create<S>()
    val state = _state.hide()
    val compositeBag = CompositeDisposable()

    override fun onDestroy() {
        _state.onComplete()
        compositeBag.clear()
        super.onDestroy()
    }

    fun navigateToDeepLink(deepLink: String) {
        val activityNavigator = ActivityNavigator(this)
        activityNavigator.navigate(
            activityNavigator.createDestination().setAction(deepLink), null, null, null
        )
    }
}
