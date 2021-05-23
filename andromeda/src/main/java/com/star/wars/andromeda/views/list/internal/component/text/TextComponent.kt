package com.star.wars.andromeda.views.list.internal.component.text

import android.annotation.SuppressLint
import android.content.Intent
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.star.wars.andromeda.R
import com.star.wars.andromeda.extensions.clickable
import com.star.wars.andromeda.extensions.dpToPixels
import com.star.wars.andromeda.extensions.unclickable
import com.star.wars.andromeda.views.list.Height
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.list.internal.ComposableHolder
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.andromeda.views.list.transformToAndroidGravity
import com.star.wars.andromeda.views.text.AndromedaTextView
import android.graphics.Color as AndroidColor

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class TextComponent : EpoxyModelWithHolder<TextComponent.Holder>() {

    @EpoxyAttribute
    lateinit var textComponentData: TextComponentData

    @EpoxyAttribute
    var deepLinkHandler: (String) -> Unit = {}

    override fun getDefaultLayout(): Int {
        return R.layout.andromeda_layout_text_component
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootContainer.setBackgroundColor(AndroidColor.parseColor(textComponentData.bgColor.value))

        with(holder.textView) {
            typographyStyle = textComponentData.textStyle
            if (!textComponentData.textSpacingExtraEnabled) {
                setLineSpacing(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        0.0f,
                        holder.rootContainer.context.resources.displayMetrics
                    ), 1.0f
                )
            }

            text = textComponentData.text

            gravity = textComponentData.gravity.transformToAndroidGravity()
            setPadding(
                textComponentData.marginsHorizontal.dpToPixels(),
                textComponentData.marginsVertical.dpToPixels(),
                textComponentData.marginsHorizontal.dpToPixels(),
                textComponentData.marginsVertical.dpToPixels()
            )

        }

        with(holder.rootContainer){
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                width = this@TextComponent.textComponentData.width.valueInPx
                height = this@TextComponent.textComponentData.height.valueInPx
                if (this@TextComponent.textComponentData.width == Width.WRAP) {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@TextComponent.textComponentData.width == Width.FILL) {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                }
                if (this@TextComponent.textComponentData.height == Height.WRAP) {
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@TextComponent.textComponentData.height == Height.FILL) {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }

            gravity = textComponentData.gravity.transformToAndroidGravity()
            setPadding(
                textComponentData.paddingHorizontal.dpToPixels(),
                textComponentData.paddingVertical.dpToPixels(),
                textComponentData.paddingHorizontal.dpToPixels(),
                textComponentData.paddingVertical.dpToPixels()
            )
            with(holder.rootContainer) {
                if (textComponentData.isViewClickable()) {
                    clickable()
                } else {
                    unclickable()
                }
            }
            /** Used for demo unrelated to production deeplink click handling **DON'T TOUCH** **/
            setOnClickListener { view ->
                deepLinkHandler(textComponentData.deepLink)
                textComponentData.navigateToOnClick?.let { navigateClassName ->
                    if (navigateClassName.isNotEmpty()) {
                        view.context.startActivity(
                            Intent(
                                view.context,
                                Class.forName(navigateClassName)
                            )
                        )
                    }
                }
            }

        }
        /** Used for demo unrelated to production deeplink click handling **DON'T TOUCH** **/
        if (textComponentData.isClickable) {
            val outValue = TypedValue()
            holder.textView.context.theme
                .resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            holder.textView.setBackgroundResource(outValue.resourceId)
        } else {
            holder.textView.background = null
        }
    }

    inner class Holder : ComposableHolder() {
        val rootContainer by bind<LinearLayout>(R.id.rootContainer)
        val textView by bind<AndromedaTextView>(R.id.textView)
    }
}
