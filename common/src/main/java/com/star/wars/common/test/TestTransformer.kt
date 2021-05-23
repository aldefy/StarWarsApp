package com.star.wars.common.test

import com.star.wars.common.base.Transformer
import io.reactivex.Observable
import io.reactivex.ObservableSource

class TestTransformer<T>: Transformer<T>() {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
    }
}
