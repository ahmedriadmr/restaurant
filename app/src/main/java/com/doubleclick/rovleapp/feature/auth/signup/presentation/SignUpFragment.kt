package com.doubleclick.restaurant.feature.auth.signup.presentation

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
import com.doubleclick.restaurant.databinding.FragmentSignUpBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.login.data.response.User
import com.doubleclick.restaurant.feature.auth.signup.data.response.UserData
import dagger.hilt.android.AndroidEntryPoint


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

        binding.confirmInformations.setOnClickListener {
            viewModel.doSignUp(
                binding.userName.text.toString(),
                binding.userEmail.text.toString(),
                binding.userPassword.text.toString(),
                binding.userPasswordConfirmation.text.toString()
            )
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.userName.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.userEmail.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.userPassword.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.userPassword.text?.length!! < 8) {
                binding.userPassword.error = "min length must be 8"
            }else{
                binding.userPassword.error = null
            }
        }
        binding.userPasswordConfirmation.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.userPasswordConfirmation.text?.length!! < 8) {
                binding.userPasswordConfirmation.error = "min length must be 8"
            }else{
                binding.userPasswordConfirmation.error = null
            }
        }

    }

    private fun handleSignUp(@Suppress("UNUSED_PARAMETER") data: UserData) {
        viewModel.doLogin(binding.userEmail.text.toString(), binding.userPassword.text.toString())

    }
    private fun handleLogin(data: User) {
        Toast.makeText(context, "You have successfully signed Up and logged in ${data.name}", Toast.LENGTH_SHORT).show()
        navigator.showHome(requireContext())
    }

    private fun showInformationButton(isEnabled: Boolean) {
        binding.confirmInformations.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        if (view != null) {
            val userName = binding.userName.text.isNullOrBlank()
            val userEmail = binding.userEmail.text.isNullOrBlank()
            val userPassword = binding.userPassword.text?.length!! >= 8
            val userPasswordConfirmation = binding.userPasswordConfirmation.text?.length!! >= 8
            showInformationButton(!userName && !userEmail && userPassword && userPasswordConfirmation)
        }
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}