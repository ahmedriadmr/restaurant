package com.doubleclick.restaurant.feature.auth.forgetPassword.presentations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentCreateNewPasswordBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNewPasswordFragment : BaseFragment(R.layout.fragment_create_new_password) {

    private val binding by viewBinding(FragmentCreateNewPasswordBinding::bind)
    private val viewModel: AuthViewModel by viewModels()
    private val navArgs: CreateNewPasswordFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(resetPassword, ::renderResetPassword)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }

        binding.resetPassword.setOnClickListener {
            viewModel.doResetPassword(navArgs.email,binding.password.text.toString(),binding.confirmPassword.text.toString())
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            checkEnableButton()
            if (binding.password.text?.length!! < 8) {
                binding.password.error = "min length must be 8"
            } else {
                binding.password.error = null
            }
        }
        binding.confirmPassword.doOnTextChanged { _, _, _, _ ->
            checkEnableButton()
            if (binding.confirmPassword.text?.length!! < 8) {
                binding.confirmPassword.error = "min length must be 8"
            } else {
                binding.confirmPassword.error = null
            }
        }

    }

    private fun renderResetPassword(data: ForgetPasswordResponse) {
        Toast.makeText(context, " ${data.message}", Toast.LENGTH_SHORT).show()
    }
    private fun sendButton(isEnabled: Boolean) {
        binding.resetPassword.isEnabled = isEnabled
    }
    private fun checkEnableButton() {
        val password = binding.password.text.length >= 8
        val confirmPassword = binding.confirmPassword.text.length >= 8
        sendButton(password && confirmPassword )
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}