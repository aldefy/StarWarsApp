package com.star.wars.search.di

import com.star.wars.search.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {

    @Binds
    abstract fun repo(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun transformer(impl: SearchTransformerImpl): SearchTransformer

    @Binds
    abstract fun useCase(impl: SearchInteractor): SearchUseCase

}
