package com.star.wars.andromeda.views.list.internal.component.empty

import android.annotation.SuppressLint
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.star.wars.andromeda.R
import com.star.wars.andromeda.views.list.internal.ComposableHolder


@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class EmptyComponent : EpoxyModelWithHolder<EmptyComponent.Holder>() {

    override fun getDefaultLayout(): Int {
        return R.layout.andromeda_layout_empty_component
    }

    inner class Holder : ComposableHolder()
}
