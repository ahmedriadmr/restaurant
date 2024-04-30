package com.doubleclick.restaurant.wallet.activity

import android.os.Bundle
import com.doubleclick.restaurant.core.platform.BaseActivity
import com.doubleclick.restaurant.databinding.ActivityCheckoutSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutSuccessActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = ActivityCheckoutSuccessBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }
}