package com.star.wars.andromeda.extensions

import android.os.Parcelable

typealias OnMenuItemClickListener = (Int) -> Unit
internal typealias ContextualActionListener = () -> Unit
typealias ComponentClickHandler = (Parcelable) -> Unit
typealias ViewComponentNotDrawnHandler = (String) -> Unit
