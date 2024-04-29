package com.doubleclick.rovleapp.core.di

import android.content.Context
import com.doubleclick.rovleapp.BuildConfig
import com.doubleclick.rovleapp.core.functional.AuthAuthenticator
import com.doubleclick.rovleapp.core.functional.HeaderInterceptor
import com.doubleclick.rovleapp.utils.Constant
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        ).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun createClient(
        cache: Cache,
        authAuthenticator: AuthAuthenticator,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder().cache(cache)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.authenticator(authAuthenticator)
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun getCache(@ApplicationContext context: Context) =
        Cache(context.cacheDir, (10 * 1000 * 1000).toLong())
}