package com.doubleclick.restaurant.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.core.platform.local.UserAccess
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appSettingsSource: AppSettingsSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeOrNull(appSettingsSource.user(), ::renderAuthenticating)
    }

    open fun renderAuthenticating(user: UserAccess?) {
        if (!isFinishing && user == null) {
            AuthPopup.showTwoButtonPopup(
                this@BaseActivity,
                "You Should Login First",
                "Do you want to go to the Login Page?",
                onOkClicked = {
                    finishAffinity()
                    navigator.showAuth(this@BaseActivity)
                },
                onCancelClicked = {
                    finish() // Finish the activity
                }
            )
        }
    }
}