package com.star.wars.landing.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.wars.common.addTo
import com.star.wars.databinding.ActivitySplashBinding
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface SplashScreen : LifecycleObserver {
    val event: LiveData<SplashEvent>

    fun bind(
        binding: ActivitySplashBinding,
        observable: Observable<SplashState>
    ): Disposable
}

class SplashScreenImpl : SplashScreen {
    private val _event = MutableLiveData<SplashEvent>()
    override val event: LiveData<SplashEvent> = _event

    override fun bind(
        binding: ActivitySplashBinding,
        observable: Observable<SplashState>
    ): Disposable {
        return CompositeDisposable().apply {
            observable.ofType(SplashState.ShowLoading::class.java)
                .map {
                    _event.value = SplashEvent.StartTimerEvent
                }
                .subscribe()
                .addTo(this)

            observable.ofType(SplashState.NavigateToNextScreen::class.java)
                .map {
                    _event.value = SplashEvent.NavigateToNextScreenEvent
                }
                .subscribe()
                .addTo(this)
        }
    }

}
