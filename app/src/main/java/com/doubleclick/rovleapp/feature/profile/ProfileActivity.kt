package com.doubleclick.rovleapp.feature.profile

import android.os.Bundle
import com.doubleclick.rovleapp.core.platform.AuthPopup
import com.doubleclick.rovleapp.core.platform.BaseActivity
import com.doubleclick.rovleapp.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Add this method to handle dialog dismissal
    override fun onDestroy() {
        // Dismiss any dialogs or popups here
        // Example: dialog?.dismiss()
        AuthPopup.dismiss()
        super.onDestroy()
    }

}
