package com.doubleclick.restaurant.core.di

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideInitializeApp(@ApplicationContext context: Context): FirebaseApp? {
        return FirebaseApp.initializeApp(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): Task<String> {
        return FirebaseMessaging.getInstance().token
    }
}