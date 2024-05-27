package com.doubleclick.restaurant.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.platform.Navigator
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.chef.presentation.ChefActivity
import com.doubleclick.restaurant.presentation.ui.admin.AdminActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var appSettingsSource: AppSettingsSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        resetUser()
        lifecycleScope.launch {
            delay(2000)

            when (appSettingsSource.user().firstOrNull()?.role) {
                "chief" -> startActivity(Intent(this@SplashActivity, ChefActivity::class.java))
                "admin" -> startActivity(Intent(this@SplashActivity, AdminActivity::class.java))
                else -> navigator.showMain(this@SplashActivity)
            }

        }
    }

    private fun resetUser() {
        lifecycleScope.launch { appSettingsSource.removeGuest() }
    }
}