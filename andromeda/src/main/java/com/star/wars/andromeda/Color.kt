package com.star.wars.andromeda

import android.graphics.Color as AndroidColor

enum class Color(val option: Int, val value: String = "#000000") {
    WHITE(value = "#ffffff", option = 0),

    BLACK(value = "#000000", option = 1),

    PRIMARY_TEXT(value = "#505050", option = 2),

    SECONDARY_TEXT(value = "#505050", option = 3),

    RED(value = "#b70000", option = 4),

    BLUE(value = "#2896E9", option = 5),

    PURPLE(value = "#7467F0", option = 6),

    BRICK_RED(value = "#BC2A13", option = 7),

    GRAY(value = "#F7F7F7", option = 8),

    GREEN(value = "#1ED2AC", option = 9),

    TRANSPARENT(value = "#00ffffff", option = 10),

    DEFAULT(value = "", option = 11),

    PRIMARY(value = "", option = 12)
}

fun Color.toColorToken(): Int {
    return when {
        this == Color.DEFAULT -> {
            -1
        }
        this == Color.PRIMARY -> {
            -999
        }
        this == Color.TRANSPARENT -> {
            AndroidColor.TRANSPARENT
        }
        else -> AndroidColor.parseColor(value)
    }
}
