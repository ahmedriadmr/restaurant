package com.doubleclick.restaurant.feature.admin.presentation

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentProfileBinding
import com.doubleclick.restaurant.feature.admin.AdminViewModel
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: AdminViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.text.text = "hello_blank_fragment"
        with(viewModel) {
            observe(logout, ::renderLogout)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }

        binding.logout.setOnClickListener {
            viewModel.doLogout()
        }
    }
    private fun renderLogout(@Suppress("UNUSED_PARAMETER") data: LogoutResponse) {
        ActivityCompat.finishAffinity(requireActivity())
        navigator.showAuth(requireActivity())
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}




