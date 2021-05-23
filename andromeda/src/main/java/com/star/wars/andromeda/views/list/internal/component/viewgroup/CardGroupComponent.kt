package com.star.wars.andromeda.views.list.internal.component.viewgroup

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.*
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.clickable
import com.star.wars.andromeda.extensions.dpToPixels
import com.star.wars.andromeda.extensions.unclickable
import com.star.wars.andromeda.toColorToken
import com.star.wars.andromeda.views.list.Height
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.list.internal.component.viewgroup.data.ViewGroupComponentData
import com.star.wars.andromeda.views.list.transformToAndroidGravity

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class CardGroupComponent(models: List<EpoxyModel<*>>) :
    EpoxyModelGroup(R.layout.andromeda_layout_card_group_component, models) {

    @EpoxyAttribute
    lateinit var viewGroup: ViewGroupComponentData

    @EpoxyAttribute
    var deepLinkHandler: (String) -> Unit = {}

    override fun bind(holder: ModelGroupHolder) {
        super.bind(holder)
        val cardView = holder.rootView as CardView
        val linearLayout =
            holder.rootView.findViewById<LinearLayout>(R.id.epoxy_model_group_child_container)

        setLinearLayoutParams(linearLayout)
        setCardViewParams(cardView)

        with(holder.rootView) {
            if (viewGroup.isViewClickable()) {
                clickable()
                setOnClickListener {
                    deepLinkHandler(viewGroup.deepLink)
                }
            } else {
                unclickable()
                setOnClickListener(null)
            }
        }
    }

    private fun setCardViewParams(cardView: CardView) {
        with(cardView) {
            radius = this@CardGroupComponent.viewGroup.radius
            val layoutParams = cardView.layoutParams as android.view.ViewGroup.MarginLayoutParams
            layoutParams.apply {
                width = this@CardGroupComponent.viewGroup.width.valueInPx.dpToPixels()
                height = this@CardGroupComponent.viewGroup.height.valueInPx.dpToPixels()
                when {
                    this@CardGroupComponent.viewGroup.width == Width.WRAP -> {
                        width = LayoutParams.WRAP_CONTENT
                    }
                    this@CardGroupComponent.viewGroup.width == Width.FILL -> {
                        width = LayoutParams.MATCH_PARENT
                    }
                    this@CardGroupComponent.viewGroup.height == Height.WRAP -> {
                        height = LayoutParams.WRAP_CONTENT
                    }
                    this@CardGroupComponent.viewGroup.height == Height.FILL -> {
                        height = LayoutParams.MATCH_PARENT
                    }
                }
                setMargins(
                    this@CardGroupComponent.viewGroup.marginsHorizontal.dpToPixels(),
                    this@CardGroupComponent.viewGroup.marginsVertical.dpToPixels(),
                    this@CardGroupComponent.viewGroup.marginsHorizontal.dpToPixels(),
                    this@CardGroupComponent.viewGroup.marginsVertical.dpToPixels()
                )
            }
            cardElevation = viewGroup.cardElevation ?: 4.0f
            foregroundGravity = Gravity.NO_GRAVITY
            setCardBackgroundColor(viewGroup.background.toColorToken())
            this.setContentPadding(
                viewGroup.paddingHorizontal.dpToPixels(),
                viewGroup.paddingVertical.dpToPixels(),
                viewGroup.paddingHorizontal.dpToPixels(),
                viewGroup.paddingVertical.dpToPixels()
            )
        }
    }

    private fun setLinearLayoutParams(linearLayout: LinearLayout) {
        linearLayout.orientation = this@CardGroupComponent.viewGroup.orientation.direction
        linearLayout.gravity = this@CardGroupComponent.viewGroup.gravity.transformToAndroidGravity()
    }
}
