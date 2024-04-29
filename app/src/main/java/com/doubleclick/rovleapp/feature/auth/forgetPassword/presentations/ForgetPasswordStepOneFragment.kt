package com.doubleclick.rovleapp.feature.auth.forgetPassword.presentations

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.databinding.FragmentForgetPasswordStepOneBinding
import com.doubleclick.rovleapp.feature.auth.AuthViewModel
import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.ForgetPasswordResponse
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