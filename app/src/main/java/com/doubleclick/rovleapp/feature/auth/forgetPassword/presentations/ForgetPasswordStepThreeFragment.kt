package com.doubleclick.restaurant.feature.auth.forgetPassword.presentations

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentForgetPasswordStepThreeBinding
import com.doubleclick.restaurant.dialog.DialogForgetPassword
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordStepThreeFragment : BaseFragment(R.layout.fragment_forget_password_step_three) {

    private val binding by viewBinding(FragmentForgetPasswordStepThreeBinding::bind)
    private val viewModel: AuthViewModel by activityViewModels()
    private val navArgs: ForgetPasswordStepThreeFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(resetPassword, ::renderResetPassword)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }


        binding.confirmInformations.setOnClickListener {
            viewModel.doResetPassword(navArgs.email, navArgs.otp, binding.password.text.toString(), binding.passwordConfirmation.text.toString())
        }

        binding.password.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.password.text?.length!! < 8) {
                binding.password.error = "min length must be 8"
            } else {
                binding.password.error = null
            }
        }
        binding.passwordConfirmation.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.passwordConfirmation.text?.length!! < 8) {
                binding.passwordConfirmation.error = "min length must be 8"
            } else {
                binding.passwordConfirmation.error = null
            }
        }
    }

    private fun showInformationButton(isEnabled: Boolean) {
        binding.confirmInformations.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        if (view != null) {
            val userPassword = binding.password.text?.length!! >= 8
            val userPasswordConfirmation = binding.passwordConfirmation.text?.length!! >= 8
            showInformationButton(userPassword && userPasswordConfirmation)
        }
    }

    private fun renderResetPassword(@Suppress("UNUSED_PARAMETER")data: ForgetPasswordResponse) {
        val dialog = DialogForgetPassword(requireActivity())
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.findViewById<TextView>(R.id.terminar)?.setOnClickListener {
            dialog.dismiss()
            viewModel.isBackPressedDispatcherEnabled(false)
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}