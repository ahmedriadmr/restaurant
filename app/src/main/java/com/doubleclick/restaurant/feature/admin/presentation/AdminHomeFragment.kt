package com.doubleclick.restaurant.feature.admin.presentation

import android.os.Bundle
import android.view.View
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentAdminHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AdminHomeFragment : BaseFragment(R.layout.fragment_admin_home) {
    private val binding by viewBinding(FragmentAdminHomeBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.text.text = "Home_fragment"
    }

}