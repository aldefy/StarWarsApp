package com.star.wars.andromeda.views.list

import android.os.Parcelable
import com.star.wars.andromeda.extensions.ViewClickBehaviour

interface ComponentDataWithClick : ComponentData, ViewClickBehaviour {
    val extraPayload: Parcelable?
    
    override fun isViewClickable(): Boolean {
        return extraPayload != null
    }
}
