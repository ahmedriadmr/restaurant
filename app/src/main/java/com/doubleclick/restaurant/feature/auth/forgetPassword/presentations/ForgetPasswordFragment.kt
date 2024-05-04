package com.doubleclick.restaurant.feature.auth.forgetPassword.presentations

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.doubleclick.restaurant.databinding.FragmentForgetPasswordBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment(R.layout.fragment_forget_password) {

    private val binding by viewBinding(FragmentForgetPasswordBinding::bind)
    private val viewModel: AuthViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(forgetPassword, ::renderForgetPassword)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }

        binding.send.setOnClickListener {
            viewModel.doForgetPassword(binding.email.text.toString())
        }
        binding.email.doOnTextChanged { _, _, _, _ ->
            checkEnableButton()
        }
    }

    private fun renderForgetPassword(data: ForgetPasswordResponse) {
        Toast.makeText(context, " ${data.message}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToOTPFragment(binding.email.text.toString()))
    }
    private fun sendButton(isEnabled: Boolean) {
        binding.send.isEnabled = isEnabled
    }
    private fun checkEnableButton() {
        val userEmail = binding.email.text.isNullOrBlank()
        sendButton(!userEmail )
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }


}