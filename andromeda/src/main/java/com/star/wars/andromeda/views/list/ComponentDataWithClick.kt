package com.star.wars.andromeda.views.list

import com.star.wars.andromeda.extensions.ViewClickBehaviour

interface ComponentDataWithClick : ComponentData, ViewClickBehaviour {
    override fun isViewClickable(): Boolean {
        return extraPayload != null
    }
}
