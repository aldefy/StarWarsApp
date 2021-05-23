package com.star.wars.andromeda.views.list

import android.view.ViewGroup

enum class Height(val valueInPx: Int) {
    WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),

    FILL(ViewGroup.LayoutParams.MATCH_PARENT),

    SMALL(valueInPx = 106),

    MEDIUM(valueInPx = 250),

    LARGE(valueInPx = 350),

    EXTRA_LARGE(valueInPx = 450),

    IMAGE_SMALL(valueInPx = 24),

    IMAGE_MEDIUM(valueInPx = 32),

    IMAGE_LARGE(valueInPx = 40),

    DIVIDER(valueInPx = 1),

    PROGRESS(valueInPx = 6),

    IMAGE_MAX(300),
}
