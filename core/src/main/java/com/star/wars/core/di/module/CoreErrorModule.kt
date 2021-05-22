package com.star.wars.core.di.module

import com.chuckerteam.chucker.api.ChuckerCollector
import com.star.wars.core.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CoreErrorModule {

    @Provides
    fun providerChuckerErrorCollector(collector: ChuckerCollector): ChuckerErrorCollectorImpl {
        return ChuckerErrorCollectorImpl(collector)
    }

    @Provides
    fun providerLogErrorCollector(): LogErrorCollectorImpl {
        return LogErrorCollectorImpl()
    }

    @Provides
    fun providerDebugErrorCollector(
        chuckerErrorCollector: ChuckerErrorCollectorImpl,
        logErrorCollector: LogErrorCollectorImpl,
    ): DebugErrorCollector {
        return DebugErrorCollector(
            chuckerErrorCollector,
            logErrorCollector
        )
    }

    @Provides
    fun providerReleaseErrorCollector(
        chuckerErrorCollector: ChuckerErrorCollectorImpl,
    ): ReleaseErrorCollector {
        return ReleaseErrorCollector(
            chuckerErrorCollector
        )
    }

    @Provides
    fun providerFeatureErrorCollector(
        debugErrorCollector: DebugErrorCollector,
        releaseErrorCollector: ReleaseErrorCollector
    ): FeatureErrorCollector {
        return FeatureErrorCollector(
            debugErrorCollector = debugErrorCollector,
            releaseErrorCollector = releaseErrorCollector
        )
    }
}
