package com.star.wars.andromeda.extensions

import com.star.wars.andromeda.views.list.ComponentData

typealias OnMenuItemClickListener = (Int) -> Unit
internal typealias ContextualActionListener = () -> Unit

typealias ViewComponentNotDrawnHandler = (ComponentData) -> Unit
