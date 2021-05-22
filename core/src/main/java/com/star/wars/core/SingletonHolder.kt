package com.star.wars.core

open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    open fun getInstance(arg: A): T {
        val singleton = instance
        if (singleton != null) {
            return singleton
        }

        return synchronized(this) {
            val singleton2 = instance
            if (singleton2 != null) {
                singleton2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
