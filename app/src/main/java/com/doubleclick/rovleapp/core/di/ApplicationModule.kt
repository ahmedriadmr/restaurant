package com.doubleclick.rovleapp.core.di

import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun userLocalSource(impl: AppSettingsSourceImpl): AppSettingsSource

}

