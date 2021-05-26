package com.star.wars.common

import java.math.RoundingMode

fun String.cmToFeetConverter(): String {
    if (this == "unknown") return this
    return try {
        val heightInFoot =
            (this.toDouble() * 0.0328084).toBigDecimal().setScale(2, RoundingMode.UP)
        heightInFoot.toString()
    } catch (e: NumberFormatException) {
        this
    }
}
