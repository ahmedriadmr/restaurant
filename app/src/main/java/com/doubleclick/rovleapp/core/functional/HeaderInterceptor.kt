package com.doubleclick.rovleapp.core.functional

import android.content.Context
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.utils.mobileId
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(
    @ApplicationContext val context: Context,
    private val appSettingsSource: AppSettingsSource
) :
    Interceptor {

    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Content-Type", "application/json")
        request.addHeader("Accept", "application/json")
        request.addHeader("mobile_id", context.mobileId()).also {
            runBlocking {
                appSettingsSource.user().first()?.let {
                    request.addHeader("Authorization", "Bearer ${it.token}")
                }
            }
        }
        return chain.proceed(request.build())
    }
}