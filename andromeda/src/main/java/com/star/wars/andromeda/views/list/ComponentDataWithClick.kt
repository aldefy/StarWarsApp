package com.star.wars.andromeda.views.list

import com.star.wars.andromeda.extensions.ViewClickBehaviour

interface ComponentDataWithClick : ComponentData, ViewClickBehaviour {
    val deepLink: String
    override fun isViewClickable(): Boolean {
        return deepLink.isNotEmpty()
    }
}
