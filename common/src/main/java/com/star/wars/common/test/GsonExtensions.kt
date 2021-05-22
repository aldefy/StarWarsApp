package com.star.wars.common.test

import com.google.gson.Gson
import java.io.InputStreamReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


fun <T> load(clazz: Class<T>, file: String): T {
    val fixtureStreamReader = InputStreamReader(clazz.classLoader!!.getResourceAsStream(file))
    return Gson().fromJson(fixtureStreamReader, clazz)
}

fun <T> load(clazz: Class<T>, file: String, gson: Gson): T {
    val fixtureStreamReader = InputStreamReader(clazz.classLoader!!.getResourceAsStream(file))
    return gson.fromJson(fixtureStreamReader, clazz)
}

fun loadAsString(file: String): String {
    return InputStreamReader(GsonExtension::class.java.classLoader!!.getResourceAsStream(file)).readText()
}

class ListParameterizedType(private val type: Type) : ParameterizedType {
    override fun getRawType(): Type {
        return ArrayList::class.java
    }

    override fun getOwnerType(): Type {
        return type
    }

    override fun getActualTypeArguments(): Array<Type> {
        return arrayOf(type)
    }

    fun <T> loadAsList(type: Type, value: String): List<T> {
        val fixtureStreamReader =
            InputStreamReader(type.javaClass.classLoader!!.getResourceAsStream(value))
        return Gson().fromJson(fixtureStreamReader, type)
    }
}

private object GsonExtension
