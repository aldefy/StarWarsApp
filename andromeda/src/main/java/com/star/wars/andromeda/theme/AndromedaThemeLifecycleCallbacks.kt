package com.star.wars.andromeda.theme

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import java.lang.reflect.InvocationTargetException

class AndromedaThemeLifecycleCallbacks(application: Application) :
    Application.ActivityLifecycleCallbacks {

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityStarted(activity: Activity) {
        val TAG = activity.javaClass.simpleName
        val currentThemeResId = getTheme(activity).themeResId

        var actualThemeResID = 0
        try {
            val clazz = ContextThemeWrapper::class.java
            val method = clazz.getMethod("getThemeResId")
            method.isAccessible = true
            actualThemeResID = method.invoke(activity) as Int
        } catch (e: NoSuchMethodException) {
            Log.e(TAG, "Failed to get theme resource ID", e)
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "Failed to get theme resource ID", e)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Failed to get theme resource ID", e)
        } catch (e: InvocationTargetException) {
            Log.e(TAG, "Failed to get theme resource ID", e)
        }

        if (currentThemeResId != actualThemeResID) {
            val intent = activity.intent
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.finish()
            activity.startActivity(intent)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val theme = getTheme(activity)
        activity.setTheme(theme.themeResId)
    }
}
