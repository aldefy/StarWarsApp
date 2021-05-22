package com.star.wars.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val bag by lazy { CompositeDisposable() }

    override fun onCleared() {
        bag.clear()
        super.onCleared()
    }
}
