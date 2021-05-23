package com.star.wars.andromeda.views.list

enum class Gravity {
    NO_GRAVITY,
    START,
    END,
    TOP,
    BOTTOM,
    CENTER,
    CENTER_HORIZONTAL,
    CENTER_VERTICAL
}

fun Gravity.transformToAndroidGravity(): Int {
    return when (this) {
        Gravity.NO_GRAVITY -> android.view.Gravity.NO_GRAVITY
        Gravity.CENTER -> android.view.Gravity.CENTER
        Gravity.CENTER_HORIZONTAL -> android.view.Gravity.CENTER_HORIZONTAL
        Gravity.CENTER_VERTICAL -> android.view.Gravity.CENTER_VERTICAL
        Gravity.START -> android.view.Gravity.START
        Gravity.END -> android.view.Gravity.END
        Gravity.TOP -> android.view.Gravity.TOP
        Gravity.BOTTOM -> android.view.Gravity.BOTTOM
    }
}
