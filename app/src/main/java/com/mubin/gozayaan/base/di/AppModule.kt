package com.mubin.gozayaan.base.di

import com.mubin.gozayaan.data.api.ApiService
import com.mubin.gozayaan.data.repo.AppRepositoryImpl
import com.mubin.gozayaan.domain.repo.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideRepository(apiService: ApiService): AppRepository = AppRepositoryImpl(apiService)

}