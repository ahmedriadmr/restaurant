package com.doubleclick.restaurant.feature.auth.forgetPassword.presentations

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
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
import com.doubleclick.restaurant.databinding.FragmentForgetPasswordStepTwoBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordStepTwoFragment : BaseFragment(R.layout.fragment_forget_password_step_two) {

    private val binding by viewBinding(FragmentForgetPasswordStepTwoBinding::bind)
    private val viewModel: AuthViewModel by viewModels()
    private val navArgs: ForgetPasswordStepTwoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            observe(verifyOtp, ::renderVerifyOtp)
            observe(forgetPassword, ::renderForgetPassword)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }


        binding.verificar.setOnClickListener {
            viewModel.doVerifyOtp(
                navArgs.email, "${binding.firstNum.text}${binding.secondNum.text}${binding.thirdNum.text}${binding.fourthNum.text}${binding.fifthNum.text}${binding.sixthNum.text}"
            )
        }

        binding.resendOtp.setOnClickListener {
            viewModel.doForgetPassword(navArgs.email)
        }

        binding.firstNum.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.secondNum.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.thirdNum.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.fourthNum.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.fifthNum.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.sixthNum.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        setupOtpInputFields()

    }

    private fun setupOtpInputFields() {
        val otpEditTexts = arrayOf(
            binding.firstNum, binding.secondNum, binding.thirdNum, binding.fourthNum, binding.fifthNum, binding.sixthNum
        )

        for (i in otpEditTexts.indices) {
            val currentEditText = otpEditTexts[i]
            val previousEditText = if (i > 0) otpEditTexts[i - 1] else null

            currentEditText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentEditText.text.isNullOrEmpty()) {
                    // If backspace is pressed and the current field is empty, move focus to the previous field
                    previousEditText?.requestFocus()
                    return@setOnKeyListener true
                }
                false
            }

            currentEditText.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrBlank()) {
                    // If the field becomes empty (due to backspace), move focus to the previous field
                    previousEditText?.requestFocus()
                } else if (text.length == 1 && i < otpEditTexts.lastIndex) {
                    // If a digit is entered and it's not the last field, move focus to the next field
                    otpEditTexts[i + 1].requestFocus()
                }
            }
        }
    }

    private fun sendButton(isEnabled: Boolean) {
        binding.verificar.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        val firstNum = binding.firstNum.text.isNullOrBlank()
        val secondNum = binding.secondNum.text.isNullOrBlank()
        val thirdNum = binding.thirdNum.text.isNullOrBlank()
        val fourthNum = binding.fourthNum.text.isNullOrBlank()
        val fifthNum = binding.fifthNum.text.isNullOrBlank()
        val sixthNum = binding.sixthNum.text.isNullOrBlank()
        sendButton(!firstNum && !secondNum && !thirdNum && !fourthNum && !fifthNum && !sixthNum)
    }

    private fun renderVerifyOtp(@Suppress("UNUSED_PARAMETER") data: ForgetPasswordResponse) {
        findNavController().navigate(
            ForgetPasswordStepTwoFragmentDirections.actionForgetPasswordStepTwoFragmentToForgetPasswordStepThreeFragment(
                navArgs.email, "${binding.firstNum.text}${binding.secondNum.text}${binding.thirdNum.text}${binding.fourthNum.text}${binding.fifthNum.text}${binding.sixthNum.text}"
            )
        )

    }

    private fun renderForgetPassword(@Suppress("UNUSED_PARAMETER") data: ForgetPasswordResponse) {

    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}