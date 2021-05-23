package com.star.wars.details.di

import com.star.wars.details.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailsModule {

    @Binds
    abstract fun repo(impl: DetailsRepositoryImpl): DetailsRepository

    @Binds
    abstract fun transformer(impl: DetailsTransformerImpl): DetailsTransformer

    @Binds
    abstract fun useCase(impl: DetailsInteractor): DetailsUseCase
}
