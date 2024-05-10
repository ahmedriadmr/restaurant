package com.doubleclick.restaurant.feature.auth.login.presentation

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
import com.doubleclick.restaurant.databinding.FragmentLoginBinding
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.restaurant.feature.auth.login.data.responseNew.NewUser
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


        binding.login.setOnClickListener {
            viewModel.doLogin(binding.email.text.toString(), binding.password.text.toString())
        }
        binding.goToSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
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

    private fun handleLogin(data: NewUser) {
        Toast.makeText(requireContext(), "You Logged In Successfully ${data.first_name}", Toast.LENGTH_SHORT).show()
        navigator.showHome(requireContext())
//        viewLifecycleOwner.lifecycleScope.launch {
//            if(viewModel.appSettingsSource.user().firstOrNull()?.role == "chief"){
//                startActivity(Intent(requireContext(), ChefActivity::class.java))
//            } else {
//                navigator.showHome(requireContext())
//            }
//        }

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