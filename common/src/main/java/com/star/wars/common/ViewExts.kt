package com.star.wars.common

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ViewBinding.show() {
    this.root.show()
}

fun ViewBinding.hide() {
    this.root.hide()
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}
