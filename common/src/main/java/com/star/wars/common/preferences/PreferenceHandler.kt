package com.star.wars.common.preferences

import java.lang.reflect.Type

/**
 * Implements preference layer.
 */
interface PreferenceHandler {
    fun readString(key: String, default: String = ""): String
    fun writeString(key: String, value: String)
    fun readLong(key: String, default: Long = 0L): Long
    fun writeLong(key: String, value: Long)
    fun readInteger(key: String, default: Int = 0): Int
    fun writeInteger(key: String, value: Int)
    fun readBoolean(key: String, default: Boolean = false): Boolean
    fun writeBoolean(key: String, value: Boolean)
    fun readFloat(key: String, default: Float = 0f): Float
    fun writeFloat(key: String, value: Float)
    fun readDouble(key: String, default: Double = 0.0): Double
    fun writeDouble(key: String, value: Double)
    fun readStringSet(key: String, default: Set<String> = mutableSetOf()): Set<String>
    fun writeStringSet(key: String, value: Set<String>)
    fun <T> readInstance(key: String, className: Class<T>): T?
    fun <T> readInstance(key: String, type: Type): T?
    fun writeInstance(key: String, classObjects: Any)

    // Synchronous methods
    fun writeBooleanSync(key: String, value: Boolean): Boolean
    fun writeInstanceSync(key: String, classObjects: Any): Boolean

    fun remove(key: String)
    fun has(key: String): Boolean
    fun getAll(): Map<String, *>
    fun clear()
}
