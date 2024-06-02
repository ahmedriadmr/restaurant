package com.doubleclick.restaurant.feature.auth.signup.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentSignUpBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.login.data.responseNew.NewUser
import com.doubleclick.restaurant.feature.auth.signup.data.responseNew.SignedUpUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewModel: AuthViewModel by viewModels()
    private val binding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            observe(signup, ::handleSignUp)
            observe(login, ::handleLogin)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }


//        binding.back.setOnClickListener {
//            findNavController().popBackStack()
//        }

        binding.guest.setOnClickListener {
            navigator.showHome(requireContext())
        }

        binding.iHaveAccount.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.signUp.setOnClickListener {
            signUp()
        }

        binding.firstName.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.lastName.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.email.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.password.text?.length!! < 8) {
                binding.password.error = "min length must be 8"
            } else {
                binding.password.error = null
            }
        }
        binding.confirmPassword.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.confirmPassword.text?.length!! < 8) {
                binding.confirmPassword.error = "min length must be 8"
            } else {
                binding.confirmPassword.error = null
            }
        }
        binding.phone.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.address.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }


    }

    private fun signUp() = lifecycleScope.launch {
        viewModel.token().collect { token ->
            viewModel.doSignUp(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.email.text.toString(),
                binding.password.text.toString(),
                binding.confirmPassword.text.toString(),
                binding.phone.text.toString(),
                binding.address.text.toString(),
                token
            )
        }
    }

    private fun handleSignUp(@Suppress("UNUSED_PARAMETER") data: SignedUpUser) {
        lifecycleScope.launch {
            viewModel.token().collect { token ->
                viewModel.doLogin(
                    binding.email.text.toString(), binding.password.text.toString(),
                    token
                )
            }
        }

    }

    private fun handleLogin(data: NewUser) {
        Toast.makeText(
            context,
            "You have successfully signed Up and logged in ${data.first_name}",
            Toast.LENGTH_SHORT
        ).show()
        navigator.showHome(requireContext())
    }

    private fun showInformationButton(isEnabled: Boolean) {
        binding.signUp.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        if (view != null) {
            val firstName = binding.firstName.text.isNullOrBlank()
            val lastName = binding.lastName.text.isNullOrBlank()
            val phone = binding.phone.text.isNullOrBlank()
            val address = binding.address.text.isNullOrBlank()
            val userEmail = binding.email.text.isNullOrBlank()
            val userPassword = binding.password.text?.length!! >= 8
            val userPasswordConfirmation = binding.confirmPassword.text?.length!! >= 8
            showInformationButton(!firstName && !lastName && !phone && !address && !userEmail && userPassword && userPasswordConfirmation)
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}