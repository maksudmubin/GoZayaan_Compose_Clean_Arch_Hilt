package com.example.trip_heaven_core.di

import com.example.trip_heaven_core.domain.repo.AppRepository
import com.example.trip_heaven_core.domain.usecases.GetDestinationsUseCase
import com.example.trip_heaven_core.domain.usecases.GetDestinationsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class UseCaseModule {
    @Provides
    fun provideGetDestinationsUseCase(repository: AppRepository): GetDestinationsUseCase =
        GetDestinationsUseCaseImpl(repository = repository)
}