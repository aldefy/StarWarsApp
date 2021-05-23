package com.star.wars.andromeda.views.list.internal.component.viewgroup

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.DeepLinkHandler
import com.star.wars.andromeda.extensions.clickable
import com.star.wars.andromeda.extensions.dpToPixels
import com.star.wars.andromeda.extensions.unclickable
import com.star.wars.andromeda.views.list.AndromedaListView
import com.star.wars.andromeda.views.list.Gravity
import com.star.wars.andromeda.views.list.Height
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.list.internal.ComposableHolder
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class GridComponent : EpoxyModelWithHolder<GridComponent.Holder>() {

    @EpoxyAttribute
    lateinit var gridComponentData: ViewGroupComponentData

    @EpoxyAttribute
    lateinit var deepLinkHandler: DeepLinkHandler

    override fun getDefaultLayout(): Int {
        return R.layout.andromeda_grid
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootView.updateLayoutParams<ViewGroup.LayoutParams> {
            width = gridComponentData.width.valueInPx.dpToPixels()
            height = gridComponentData.height.valueInPx.dpToPixels()
            if (gridComponentData.width == Width.WRAP) {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            if (gridComponentData.width == Width.FILL) {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            if (gridComponentData.height == Height.WRAP) {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            if (gridComponentData.height == Height.FILL) {
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
        holder.rootView.setPadding(
            gridComponentData.paddingHorizontal.dpToPixels(),
            gridComponentData.paddingVertical.dpToPixels(),
            gridComponentData.paddingHorizontal.dpToPixels(),
            gridComponentData.paddingVertical.dpToPixels()
        )
        holder.gridERV.setDeepLinkHandler(deepLinkHandler)
        holder.gridERV.setSpanCount(gridComponentData.spanCount)
        holder.gridERV.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topMargin = gridComponentData.marginsVertical.dpToPixels()
            bottomMargin = gridComponentData.marginsVertical.dpToPixels()
            marginStart = gridComponentData.marginsHorizontal.dpToPixels()
            marginEnd = gridComponentData.marginsHorizontal.dpToPixels()

            when (gridComponentData.gravity) {
                Gravity.START -> {
                    this.startToStart = ConstraintSet.PARENT_ID
                }
                Gravity.END -> {
                    this.endToEnd = ConstraintSet.PARENT_ID
                }
                Gravity.TOP -> {
                    this.topToTop = ConstraintSet.PARENT_ID
                }
                Gravity.BOTTOM -> {
                    this.bottomToBottom = ConstraintSet.PARENT_ID
                }
                Gravity.CENTER -> {
                    this.topToTop = ConstraintSet.PARENT_ID
                    this.bottomToBottom = ConstraintSet.PARENT_ID
                    this.endToEnd = ConstraintSet.PARENT_ID
                    this.startToStart = ConstraintSet.PARENT_ID
                }
                Gravity.CENTER_VERTICAL -> {
                    this.topToTop = ConstraintSet.PARENT_ID
                    this.bottomToBottom = ConstraintSet.PARENT_ID
                }
                Gravity.CENTER_HORIZONTAL -> {
                    this.endToEnd = ConstraintSet.PARENT_ID
                    this.startToStart = ConstraintSet.PARENT_ID
                }
                else -> {
                    this.startToStart = ConstraintSet.PARENT_ID
                    this.topToTop = ConstraintSet.PARENT_ID
                }
            }
        }

        if (gridComponentData.isViewClickable()) {
            holder.rootView.clickable()
            holder.rootView.setOnClickListener {
                deepLinkHandler(gridComponentData.deepLink)
            }
        } else {
            holder.rootView.unclickable()
            holder.rootView.setOnClickListener(null)
        }
        holder.gridERV.setUpComponents(gridComponentData.children)
    }

    class Holder : ComposableHolder() {
        val gridERV by bind<AndromedaListView>(R.id.ervGrid)
        val rootView by bind<ConstraintLayout>(R.id.root)
    }
}
