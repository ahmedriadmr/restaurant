package com.doubleclick.rovleapp.core.platform

import android.app.Activity
import android.content.Context
import com.doubleclick.rovleapp.activities.HomeActivity
import com.doubleclick.rovleapp.core.functional.Authenticator
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(private val authenticator: Authenticator) {

    suspend fun showMain(context: Context?) {
        when (authenticator.userLogin()) {
            true -> showHome(context)
            false -> {
                authenticator.userNotLogin()
                showHome(context)
            }
        }
    }

    fun showAuth(context: Activity?) = context?.startActivity(AuthActivity.callingIntent(context))

    fun showHome(context: Context?) = context?.startActivity(HomeActivity.callingIntent(context))
}