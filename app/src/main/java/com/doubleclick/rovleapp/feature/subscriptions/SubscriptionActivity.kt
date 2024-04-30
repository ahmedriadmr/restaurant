package com.doubleclick.restaurant.feature.subscriptions

import android.os.Bundle
import com.doubleclick.restaurant.core.platform.AuthPopup
import com.doubleclick.restaurant.core.platform.BaseActivity
import com.doubleclick.restaurant.databinding.ActivitySubscriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionActivity : BaseActivity() {
    private lateinit var binding: ActivitySubscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    override fun onDestroy() {
        AuthPopup.dismiss()
        super.onDestroy()
    }
}