package com.star.wars.common.di

import com.star.wars.common.HttpsTransformer
import com.star.wars.common.HttpsTransformerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
    @Provides
    fun httpsTransformer(): HttpsTransformer {
        return HttpsTransformerImpl()
    }
}
