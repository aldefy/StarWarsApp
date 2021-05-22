package com.star.wars.core

import com.chuckerteam.chucker.api.ChuckerCollector
import timber.log.Timber
import javax.inject.Inject

interface ErrorCollector {
    fun catchError(tag: String, error: Throwable)
    fun catchError(tag: String, error: Exception)
}

class ChuckerErrorCollectorImpl(
    private val collector: ChuckerCollector
) : ErrorCollector {
    override fun catchError(tag: String, error: Throwable) {
        collector.onError(tag, error)
    }

    override fun catchError(tag: String, error: Exception) {
        collector.onError(tag, error)
    }
}

class LogErrorCollectorImpl : ErrorCollector {
    override fun catchError(tag: String, error: Throwable) {
        Timber.tag(tag).e(error)
    }

    override fun catchError(tag: String, error: Exception) {
        Timber.tag(tag).e(error)
    }
}

class DebugErrorCollector @Inject constructor(
    private val chuckerErrorCollector: ChuckerErrorCollectorImpl,
    private val logErrorCollector: LogErrorCollectorImpl,
) : ErrorCollector {
    override fun catchError(tag: String, error: Throwable) {
        chuckerErrorCollector.catchError(tag, error)
        logErrorCollector.catchError(tag, error)
    }

    override fun catchError(tag: String, error: Exception) {
        chuckerErrorCollector.catchError(tag, error)
        logErrorCollector.catchError(tag, error)
    }
}

class ReleaseErrorCollector @Inject constructor(
    private val chuckerErrorCollector: ChuckerErrorCollectorImpl
) : ErrorCollector {
    override fun catchError(tag: String, error: Throwable) {
        chuckerErrorCollector.catchError(tag, error)
    }

    override fun catchError(tag: String, error: Exception) {
        chuckerErrorCollector.catchError(tag, error)
    }
}

class FeatureErrorCollector @Inject constructor(
    private val releaseErrorCollector: ReleaseErrorCollector,
    private val debugErrorCollector: DebugErrorCollector
) : ErrorCollector {
    override fun catchError(tag: String, error: Throwable) {
        if (BuildConfig.DEBUG)
            debugErrorCollector.catchError(tag, error)
        else
            releaseErrorCollector.catchError(tag, error)
    }

    override fun catchError(tag: String, error: Exception) {
        if (BuildConfig.DEBUG)
            debugErrorCollector.catchError(tag, error)
        else
            releaseErrorCollector.catchError(tag, error)
    }
}
