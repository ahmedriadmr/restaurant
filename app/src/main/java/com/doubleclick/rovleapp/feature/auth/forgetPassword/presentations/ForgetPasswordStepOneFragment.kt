package com.doubleclick.restaurant.feature.auth.forgetPassword.presentations

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
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
import com.doubleclick.restaurant.databinding.FragmentForgetPasswordStepOneBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordStepOneFragment : BaseFragment(R.layout.fragment_forget_password_step_one) {

    private val binding by viewBinding(FragmentForgetPasswordStepOneBinding::bind)
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            observe(forgetPassword, ::renderForgetPassword)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }
        binding.email.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.send.setOnClickListener {
            viewModel.doForgetPassword(binding.email.text.toString())
        }
    }

    private fun renderForgetPassword(@Suppress("UNUSED_PARAMETER") data: ForgetPasswordResponse) {
        findNavController().navigate(
            ForgetPasswordStepOneFragmentDirections.actionForgetPasswordStepOneFragmentToForgetPasswordStepTwoFragment(
                binding.email.text.toString() )
        )
    }
    private fun sendButton(isEnabled: Boolean) {
        binding.send.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        val userEmail = binding.email.text.isNullOrBlank()
        sendButton(!userEmail)
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}