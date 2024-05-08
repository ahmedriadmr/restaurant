package com.doubleclick.restaurant.feature.home.presentation

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentUserProfileBinding
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import com.doubleclick.restaurant.feature.home.data.userProfile.UserProfileData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile) {

    private val binding by viewBinding(FragmentUserProfileBinding::bind)
    private val viewModel: HomeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            observe(profile,:: renderUserProfile)
            observe(logout, ::renderLogout)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getUserProfile()
        }
        onClick()
    }

    private fun onClick() {
        binding.accountFragment.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToAccountFragment())
        }
        binding.myOrdersFragment.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToMyOrdersFragment())
        }
//        binding.infoFragment.setOnClickListener {
//
//        }
        binding.logout.setOnClickListener {
            viewModel.doLogout()
        }
    }

    private fun renderUserProfile(data: UserProfileData) {

        binding.name.text = data.first_name


    }

    private fun renderLogout(@Suppress("UNUSED_PARAMETER") data: LogoutResponse) {
        finishAffinity(requireActivity())
        navigator.showAuth(requireActivity())
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}