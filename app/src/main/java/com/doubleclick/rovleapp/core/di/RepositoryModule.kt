package com.doubleclick.restaurant.core.di

import com.doubleclick.restaurant.feature.auth.data.AuthRepository
import com.doubleclick.rovleapp.feature.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(dataSource: AuthRepository.Network): AuthRepository = dataSource

    @Provides
    @Singleton
    fun provideHomeRepository(dataSource: HomeRepository.Network): HomeRepository = dataSource


}