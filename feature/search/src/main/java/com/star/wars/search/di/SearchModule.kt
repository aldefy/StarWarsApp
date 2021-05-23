package com.star.wars.search.di

import com.star.wars.search.domain.SearchInteractor
import com.star.wars.search.domain.SearchRepository
import com.star.wars.search.domain.SearchRepositoryImpl
import com.star.wars.search.domain.SearchUseCase
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
    abstract fun useCase(impl: SearchInteractor): SearchUseCase

}
