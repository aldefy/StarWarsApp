package com.star.wars.common.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

interface StarWarsLifecycleDelegate {
    fun onAppBackgrounded()
    fun onAppForegrounded()
    fun onStart()
    fun onStop()
    fun onDestroy()
    fun onCreate()
    fun onResume()
    fun onPause()
}

class MonkLifecycleListener(private val delegate: StarWarsLifecycleDelegate) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        delegate.onAppForegrounded()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        delegate.onAppBackgrounded()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        delegate.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        delegate.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        delegate.onDestroy()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        delegate.onCreate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        delegate.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        delegate.onPause()
    }
}
