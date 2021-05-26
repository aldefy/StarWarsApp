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
    abstract fun characterDetailsTransformer(impl: DetailsFetchCharacterTransformerImpl): DetailsFetchCharacterTransformer

    @Binds
    abstract fun filmsDetailsTransformer(impl: DetailsFetchFilmsTransformerImpl): DetailsFetchFilmsTransformer

    @Binds
    abstract fun speciesDetailsTransformer(impl: DetailsFetchSpeciesTransformerImpl): DetailsFetchSpeciesTransformer

    @Binds
    abstract fun planetDetailsTransformer(impl: DetailsFetchPlanetTransformerImpl): DetailsFetchPlanetTransformer

    @Binds
    abstract fun fetchCharacterUseCase(impl: DetailsFetchCharacterInteractor): DetailsFetchCharacterUseCase

    @Binds
    abstract fun fetchFilmsUseCase(impl: DetailsFetchFilmsInteractor): DetailsFetchFilmsUseCase

    @Binds
    abstract fun fetchSpeciesUseCase(impl: DetailsFetchSpeciesInteractor): DetailsFetchSpeciesUseCase

    @Binds
    abstract fun fetchPlanetUseCase(impl: DetailsFetchPlanetInteractor): DetailsFetchPlanetUseCase
}
