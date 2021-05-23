package com.star.wars.andromeda.views.list

import android.view.ViewGroup

enum class Width(val valueInPx: Int) {
    WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),

    FILL(ViewGroup.LayoutParams.MATCH_PARENT),

    SMALL(150),

    MEDIUM(250),

    LARGE(350),

    EXTRA_LARGE(450),

    IMAGE_SMALL(24),

    IMAGE_MEDIUM(32),

    IMAGE_LARGE(40),

    IMAGE_MAX(300)
}
