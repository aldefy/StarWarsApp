package com.star.wars.andromeda.views.list.internal.component.text.data

import com.spacemonk.andromeda.component.*
import com.star.wars.andromeda.Color
import com.star.wars.andromeda.views.list.ComponentDataWithClick
import com.star.wars.andromeda.views.list.Gravity
import com.star.wars.andromeda.views.list.Height
import com.star.wars.andromeda.views.list.Width
import com.star.wars.andromeda.views.text.TypographyStyle
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextComponentData(
    override val width: Width = Width.FILL,
    override val height: Height = Height.WRAP,
    override val id: String = "",
    override val gravity: Gravity = Gravity.NO_GRAVITY,
    override val viewType: String = "text",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    override val deepLink: String = "",
    var navigateToOnClick: String? = null,
    val isClickable: Boolean = false,
    val text: String = "",
    val size: Float = 16.0f,
    val textStyle: TypographyStyle = TypographyStyle.TITLE_MODERATE_BOLD_DEFAULT,
    val textSpacingExtraEnabled: Boolean = true,
    val showDivider: Boolean = true,
    val bgColor: Color = Color.TRANSPARENT,
    val marginsHorizontal: Int = 0,
    val marginsVertical: Int = 0,
) : ComponentDataWithClick
