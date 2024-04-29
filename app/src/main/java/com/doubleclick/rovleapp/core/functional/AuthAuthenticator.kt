package com.doubleclick.rovleapp.core.functional

import com.doubleclick.rovleapp.core.functional.Authenticator.Companion.GUEST
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val appSettingsSource: AppSettingsSource
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val user = appSettingsSource.user().firstOrNull() ?: return@runBlocking null
            appSettingsSource.saveUserAccess(null)
            if (user.token == GUEST) appSettingsSource.setGuest()
            return@runBlocking null
        }
    }
}