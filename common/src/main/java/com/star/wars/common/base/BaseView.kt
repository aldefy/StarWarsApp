package com.star.wars.common.base

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseView<V : ViewModel, S : ViewState>(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), StarWarsLifecycleDelegate, LifecycleOwner {

    abstract val clazz: Class<V>
    val _state = PublishSubject.create<S>()
    val state = _state.hide()
    val compositeBag = CompositeDisposable()
    abstract val registry: LifecycleRegistry

    override fun getLifecycle(): Lifecycle {
        return registry
    }

    override fun onDestroy() {
        _state.onComplete()
        compositeBag.clear()
    }

    override fun onAppBackgrounded() {
    }

    override fun onAppForegrounded() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onCreate() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }
}
