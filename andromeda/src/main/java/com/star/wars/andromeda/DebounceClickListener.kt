package com.star.wars.andromeda

import android.os.Handler
import android.view.View

abstract class DebounceClickListener : View.OnClickListener {

    companion object {
        @JvmStatic
        private var enabled = true
        @JvmStatic
        private val ENABLE_AGAIN = Runnable { enabled = true }
        @JvmStatic
        private val handler = Handler()
    }

    override fun onClick(view: View) {
        if (enabled) {
            enabled = false
            handler.postDelayed(ENABLE_AGAIN, 500)
            doClick(view)
        }
    }

    open fun doClick(v: View) {
        throw NotImplementedError("not implemented")
    }
}
