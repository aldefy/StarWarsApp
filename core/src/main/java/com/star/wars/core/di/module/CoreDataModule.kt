package com.star.wars.core.di.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.star.wars.core.CoreConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class CoreDataModule {

    @Provides
    fun provideCoreConfig(): CoreConfig {
        return CoreConfig()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun providerChuckerCollector(@ApplicationContext context: Context): ChuckerCollector {
        return ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.FOREVER
        )
    }

    @Provides
    fun providesChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(12_000_0L)
            .build()
    }

    @Provides
    fun providesGsonFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun providesOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .addNetworkInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    fun providesRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        coreConfig: CoreConfig
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(coreConfig.getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}
