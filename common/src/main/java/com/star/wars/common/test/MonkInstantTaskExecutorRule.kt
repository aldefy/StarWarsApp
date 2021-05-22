package com.star.wars.common.test

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor

object MonkInstantTaskExecutorRule {
    @SuppressLint("RestrictedApi")
    fun start() {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread() = true

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    @SuppressLint("RestrictedApi")
    fun tearDown() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}
