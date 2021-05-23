package com.star.wars.common.base

import io.reactivex.ObservableTransformer

abstract class Transformer<T> : ObservableTransformer<T, T>
