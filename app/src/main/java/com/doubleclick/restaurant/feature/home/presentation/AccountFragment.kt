package com.doubleclick.restaurant.feature.home.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
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
import com.doubleclick.restaurant.databinding.FragmentAccountBinding
import com.doubleclick.restaurant.feature.home.data.UpdateProfileResponse
import com.doubleclick.restaurant.feature.home.data.userProfile.UserProfileData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private val binding by viewBinding(FragmentAccountBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            observe(profile, ::renderUserProfile)
            observe(updateProfile, ::renderUpdateProfileState)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getUserProfile()
        }
        onClick()
    }

    private fun onClick() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.edit.setOnClickListener {
            val password = binding.password.text.takeIf { it.isNotEmpty() }?.toString()
            val passwordConfirmation = binding.passwordConfirmation.text.takeIf { it.isNotEmpty() }?.toString()
            viewModel.updateProfile(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.email.text.toString(),
                password,
                passwordConfirmation,
                binding.phone.text.toString(),
                binding.address.text.toString()
            )
        }

    }

    private fun renderUserProfile(data: UserProfileData) {

        binding.firstName.text = Editable.Factory.getInstance().newEditable(data.first_name)
        binding.lastName.text = Editable.Factory.getInstance().newEditable(data.last_name)
        binding.email.text = Editable.Factory.getInstance().newEditable(data.email)
        binding.phone.text = Editable.Factory.getInstance().newEditable(data.phone)
        binding.address.text = Editable.Factory.getInstance().newEditable(data.address)


    }

    private fun renderUpdateProfileState(data: UpdateProfileResponse) {

        Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()


    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}