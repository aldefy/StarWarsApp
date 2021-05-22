package com.star.wars.common.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.exceptions.CompositeException
import timber.log.Timber
import java.lang.reflect.Type

class PreferenceHandlerImpl(
    context: Context,
    private val prefFile: String? = null
) : PreferenceHandler {

    private val context = context.applicationContext
    private val gson = Gson()

    override fun <T> readInstance(key: String, className: Class<T>): T? =
        try {
            gson.fromJson(readString(key), className)
        } catch (e: JsonSyntaxException) {
            val exception = CompositeException(
                e,
                RuntimeException("readInstance error: Key: $key :: Class:: $className")
            )
            Timber.e(exception.stackTrace.toString())
            null
        }

    override fun <T> readInstance(key: String, type: Type): T? =
        try {
            gson.fromJson(readString(key), type)
        } catch (e: JsonSyntaxException) {
            val exception =
                CompositeException(
                    e,
                    RuntimeException("readInstance error: Key: $key :: Type:: $type")
                )

            Timber.e(exception.stackTrace.toString())
            null
        }

    override fun writeInstance(key: String, classObjects: Any) {
        writeString(key, gson.toJson(classObjects))
    }

    override fun readString(key: String, default: String): String =
        if (has(key)) getPreference(key, default) else default

    override fun readLong(key: String, default: Long): Long =
        if (has(key)) getPreference(key, default) else default

    override fun readInteger(key: String, default: Int): Int =
        if (has(key)) getPreference(key, default) else default

    override fun readFloat(key: String, default: Float): Float =
        if (has(key)) getPreference(key, default) else default

    override fun readDouble(key: String, default: Double): Double =
        if (has(key)) getPreference(key, default.toLong()).toDouble() else default

    override fun readBoolean(key: String, default: Boolean) = getPreference(key, default)

    override fun readStringSet(key: String, default: Set<String>): Set<String> =
        if (has(key)) getPreference(key, setOf()) else setOf()

    override fun writeString(key: String, value: String) = setPreference(key, value)
    override fun writeLong(key: String, value: Long) = setPreference(key, value)
    override fun writeInteger(key: String, value: Int) = setPreference(key, value)
    override fun writeBoolean(key: String, value: Boolean) = setPreference(key, value)
    override fun writeFloat(key: String, value: Float) = setPreference(key, value)
    override fun writeDouble(key: String, value: Double) = setPreference(key, value.toLong())
    override fun writeStringSet(key: String, value: Set<String>) = setPreference(key, value)

    override fun remove(key: String) = prefs().edit().remove(key).apply()

    override fun has(key: String): Boolean = prefs().contains(key)

    override fun clear() = prefs().edit().clear().apply()

    /**
     * Synchronous methods.
     */
    override fun writeBooleanSync(key: String, value: Boolean) = setPreferenceSync(key, value)

    override fun writeInstanceSync(key: String, classObjects: Any) = writeStringSync(key, gson.toJson(classObjects))

    private fun writeStringSync(key: String, value: String) = setPreferenceSync(key, value)

    private fun prefs(): SharedPreferences =
        if (prefFile != null) customPrefs(context, prefFile) else defaultPrefs(context)

    private fun defaultPrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private fun customPrefs(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    private fun <T> getPreference(key: String, default: T): T = with(prefs()) {
        val res: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default as String) as String
            is Int -> getInt(key, default as Int)
            is Boolean -> getBoolean(key, default as Boolean)
            is Float -> getFloat(key, default as Float)
            is Set<*> -> getStringSet(key, default as Set<String>) as Set<String>
            else -> throw IllegalArgumentException("No argument matches")
        }
        res as T
    }

    private fun <T> setPreference(key: String, value: T) = with(prefs().edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Set<*> -> putStringSet(key, value as Set<String>)
            else -> throw IllegalArgumentException("No Argument matches")
        }.apply()
    }

    private fun <T> setPreferenceSync(key: String, value: T) = with(prefs().edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Set<*> -> putStringSet(key, value as Set<String>)
            else -> throw IllegalArgumentException("No Argument matches")
        }.commit()
    }

    override fun getAll(): Map<String, *> = prefs().all
}
