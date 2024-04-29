package com.doubleclick.rovleapp.feature.auth.login.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.doubleclick.rovleapp.databinding.FragmentLoginBinding
import com.doubleclick.rovleapp.feature.auth.AuthViewModel
import com.doubleclick.rovleapp.feature.auth.login.data.response.User
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val viewModel: AuthViewModel by viewModels()
    private val binding by viewBinding(FragmentLoginBinding::bind)
    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observe(login, ::handleLogin)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }

        binding.forgetPassword.setOnClickListener {
             findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.login.setOnClickListener {
            viewModel.doLogin(binding.email.text.toString(), binding.password.text.toString())
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
    }

    private fun handleLogin(data: User) {
        Toast.makeText(requireContext(), "You Logged In Successfully ${data.name}", Toast.LENGTH_SHORT).show()
        navigator.showHome(requireContext())
    }

    private fun showInformationButton(isEnabled: Boolean) {
        binding.login.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        val userEmail = binding.email.text.isNullOrBlank()
        val userPassword = binding.password.text?.length!! >= 8
        showInformationButton(!userEmail && userPassword)
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}