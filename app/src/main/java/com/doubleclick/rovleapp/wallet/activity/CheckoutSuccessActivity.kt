package com.doubleclick.rovleapp.wallet.activity

import android.os.Bundle
import com.doubleclick.rovleapp.core.platform.BaseActivity
import com.doubleclick.rovleapp.databinding.ActivityCheckoutSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutSuccessActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = ActivityCheckoutSuccessBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }
}