package com.star.wars.andromeda.views.divider.internal

import android.content.Context
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import com.star.wars.andromeda.extensions.toPxFloat
import com.star.wars.andromeda.extensions.toPxInt
import com.star.wars.andromeda.tokens.fill_mute_primary

internal class DottedDivider(context: Context) : Divider {

    override val height = 1f.toPxInt(context)

    override val dividerLineHeight = height

    override val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = context.fill_mute_primary
        style = Paint.Style.STROKE
        strokeWidth = height.toFloat()
        val interval = 2f.toPxFloat(context)
        pathEffect = DashPathEffect(floatArrayOf(interval, interval), 0f)
    }

    override val backgroundPaint: Paint? = null
}
