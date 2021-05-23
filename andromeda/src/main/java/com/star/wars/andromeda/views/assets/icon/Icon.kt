package com.star.wars.andromeda.views.assets.icon

import com.star.wars.andromeda.R


enum class Icon {
    NOTIFICATION {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_bell
        }
    },
    NAVIGATION_BACK {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_back
        }
    },
    NAVIGATION_CANCEL {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_cancel
        }
    },
    SHARE {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_share
        }
    },
    SWITCH_THEME {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_switch_theme
        }
    },
    NAVIGATION_OPTIONS {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_options
        }
    },
    NAVIGATION_24_OVERFLOW {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_overflow
        }
    },
    SEARCH {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_search
        }
    };


    abstract fun getDrawableResId(): Int
}
