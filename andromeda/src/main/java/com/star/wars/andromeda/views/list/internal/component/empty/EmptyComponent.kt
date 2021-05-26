package com.star.wars.andromeda.views.list.internal.component.empty

import android.annotation.SuppressLint
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.star.wars.andromeda.R
import com.star.wars.andromeda.views.list.BaseComponentData
import com.star.wars.andromeda.views.list.internal.ComposableHolder
import com.star.wars.andromeda.views.list.internal.component.empty.data.EmptyComponentData
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData


@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class EmptyComponent : EpoxyModelWithHolder<EmptyComponent.Holder>() {

    @EpoxyAttribute
    lateinit var emptyComponentData: BaseComponentData

    override fun getDefaultLayout(): Int {
        return R.layout.andromeda_layout_empty_component
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        //Lint Fix
        emptyComponentData.id
    }

    inner class Holder : ComposableHolder()
}
