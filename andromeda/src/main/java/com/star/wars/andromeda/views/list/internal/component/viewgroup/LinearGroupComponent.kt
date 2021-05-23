package com.star.wars.andromeda.views.list.internal.component.viewgroup

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.airbnb.epoxy.*
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.clickable
import com.star.wars.andromeda.extensions.dpToPixels
import com.star.wars.andromeda.extensions.unclickable
import com.star.wars.andromeda.views.list.Height
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.andromeda.views.list.transformToAndroidGravity

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class LinearGroupComponent(models: List<EpoxyModel<*>>) :
    EpoxyModelGroup(R.layout.andromeda_layout_linear_group_component, models) {

    @EpoxyAttribute
    lateinit var viewGroupData: ViewGroupComponentData

    @EpoxyAttribute
    var deepLinkHandler: (String) -> Unit = {}

    override fun bind(holder: ModelGroupHolder) {
        super.bind(holder)
        val root = holder.rootView as LinearLayout
        with(root) {
            orientation = this@LinearGroupComponent.viewGroupData.orientation.direction
            setBackgroundColor(android.graphics.Color.parseColor(viewGroupData.background.value))
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                width = this@LinearGroupComponent.viewGroupData.width.valueInPx
                height = this@LinearGroupComponent.viewGroupData.height.valueInPx
                if (this@LinearGroupComponent.viewGroupData.width == Width.WRAP) {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@LinearGroupComponent.viewGroupData.width == Width.FILL) {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                }
                if (this@LinearGroupComponent.viewGroupData.height == Height.WRAP) {
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@LinearGroupComponent.viewGroupData.height == Height.FILL) {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
                setMargins(
                    viewGroupData.marginsHorizontal.dpToPixels(),
                    viewGroupData.marginsVertical.dpToPixels(),
                    viewGroupData.marginsHorizontal.dpToPixels(),
                    viewGroupData.marginsVertical.dpToPixels()
                )
                gravity =
                    this@LinearGroupComponent.viewGroupData.gravity.transformToAndroidGravity()
                setPadding(
                    viewGroupData.paddingHorizontal.dpToPixels(),
                    viewGroupData.paddingVertical.dpToPixels(),
                    viewGroupData.paddingHorizontal.dpToPixels(),
                    viewGroupData.paddingVertical.dpToPixels()
                )
                isBaselineAligned = false
            }

            if (viewGroupData.deepLink.isNotEmpty()) {
                clickable()
                setOnClickListener {
                    deepLinkHandler(viewGroupData.deepLink)
                }
            } else {
                unclickable()
                setOnClickListener(null)
            }
        }
    }
}
