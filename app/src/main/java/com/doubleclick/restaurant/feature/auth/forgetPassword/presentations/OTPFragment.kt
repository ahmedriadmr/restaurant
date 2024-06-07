package com.doubleclick.restaurant.feature.auth.forgetPassword.presentations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentOTPBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : BaseFragment(R.layout.fragment_o_t_p) {

    private val binding by viewBinding(FragmentOTPBinding::bind)
    private val viewModel: AuthViewModel by viewModels()
    private val navArgs: OTPFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(verifyOtp, ::renderVerifyOtp)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.verify.setOnClickListener {
            viewModel.doVerifyOtp(binding.code.text.toString())
        }
        binding.code.doOnTextChanged { _, _, _, _ ->
            checkEnableButton()
        }

    }

    private fun renderVerifyOtp(data: ForgetPasswordResponse) {
        Toast.makeText(context, " ${data.message}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(OTPFragmentDirections.actionOTPFragmentToCreateNewPasswordFragment(navArgs.email))
    }
    private fun sendButton(isEnabled: Boolean) {
        binding.verify.isEnabled = isEnabled
    }
    private fun checkEnableButton() {
        val code = binding.code.text.isNullOrBlank()
        sendButton(!code )
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}