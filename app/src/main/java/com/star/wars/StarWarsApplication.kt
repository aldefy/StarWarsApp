package com.star.wars

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.star.wars.andromeda.theme.AndromedaThemeLifecycleCallbacks
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class StarWarsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        plantTimberTree()
        initComponents()
    }

    private fun initComponents() {
        AndromedaThemeLifecycleCallbacks(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun plantTimberTree() {
        if (BuildConfig.DEBUG) {
            val logFormatting = PrettyFormatStrategy.newBuilder()
                .methodCount(0)
                .tag("Star wars")
                .build()
            Logger.addLogAdapter(AndroidLogAdapter(logFormatting))
            Timber.plant(Timber.DebugTree())
        }
    }
}
