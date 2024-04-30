package com.doubleclick.restaurant.core.di

import com.doubleclick.restaurant.feature.auth.data.AuthRepository
import com.doubleclick.restaurant.feature.passport.PassportRepository
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
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
    fun provideShopRepository(dataSource: ShopRepository.Network): ShopRepository = dataSource

    @Provides
    @Singleton
    fun passportRepository(dataSource: PassportRepository.Network): PassportRepository = dataSource

    @Provides
    @Singleton
    fun profileRepository(dataSource: ProfileRepository.Network): ProfileRepository = dataSource

    @Provides
    @Singleton
    fun subscriptionsRepository(dataSource: SubscriptionsRepository.Network): SubscriptionsRepository =
        dataSource
}