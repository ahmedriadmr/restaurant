package com.doubleclick.restaurant.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.core.platform.local.UserAccess
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appSettingsSource: AppSettingsSource

    // NavController reference
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeOrNull(appSettingsSource.user(), ::renderAuthenticating)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Check if the current activity has a navController for the given ID
        try {
            navController = findNavController(R.id.home_fragment)
        } catch (e: IllegalArgumentException) {
            // Handle the absence of the navController gracefully
            // For example, log the error or handle it in a way specific to ChefActivity
        }
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
                    // Navigate to homeFragment instead of finishing activity
                    navController.navigate(R.id.homeFragment)
                }
            )
        }
    }
}