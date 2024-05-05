package com.doubleclick.restaurant.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.platform.Navigator
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
            navigator.showMain(this@SplashActivity)
        }
    }

    private fun resetUser() {
        lifecycleScope.launch { appSettingsSource.removeGuest() }
    }
}