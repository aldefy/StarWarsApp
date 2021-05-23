package com.star.wars.details.di

import com.star.wars.details.model.DetailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class DetailsDataModule {
    @Provides
    fun providesDetailsApi(retrofit: Retrofit): DetailsApi {
        return retrofit.create(DetailsApi::class.java)
    }
}
