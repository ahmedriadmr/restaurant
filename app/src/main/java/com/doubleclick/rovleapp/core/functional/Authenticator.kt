package com.doubleclick.restaurant.core.functional

import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor(val appSettingsSource: AppSettingsSource) {
    //Here you should put your own logic to return whether the user
    //is authenticated or not
    suspend fun userLogin() = appSettingsSource.user().firstOrNull() != null
    suspend fun userNotLogin() = appSettingsSource.setGuest()

    companion object {
        const val GUEST = "-1"
    }
}
