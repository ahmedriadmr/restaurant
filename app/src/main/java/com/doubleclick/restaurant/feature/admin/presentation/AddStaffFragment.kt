package com.doubleclick.restaurant.presentation.ui.admin.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.adapter.spinner.SpinnerAdapter
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentAddStaffBinding
import com.doubleclick.restaurant.feature.admin.AdminViewModel
import com.doubleclick.restaurant.feature.admin.data.addStaff.request.AddStaffRequest
import com.doubleclick.restaurant.feature.admin.data.addStaff.response.AddStaffData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddStaffFragment : BaseFragment(R.layout.fragment_add_staff) {
    private val binding by viewBinding(FragmentAddStaffBinding::bind)
    private val viewModel: AdminViewModel by viewModels()
    private val list = listOf("Waiter", "Chef")
    lateinit var selectedPeriodic: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observe(addStaff, ::renderAddStaff)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }
        setSpinner()

        binding.role.adapter = SpinnerAdapter(
            requireActivity(), list, R.color.white, Gravity.CENTER_VERTICAL
        )
        binding.create.setOnClickListener {
            addStaff()
        }

        binding.firstName.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.lastName.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.emailAddress.doOnTextChanged { _, _, _, _ ->
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

    private fun addStaff() = lifecycleScope.launch {

        viewModel.token().collect { token ->
            viewModel.addStaff(
                AddStaffRequest(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.emailAddress.text.toString(),
                    binding.phone.text.toString(),
                    binding.address.text.toString(),
                    binding.password.text.toString(),
                    binding.confirmPassword.text.toString(),
                    token,
                    when (selectedPeriodic) {
                        "Waiter" -> "waiter"
                        "Chef" -> "chief"
                        else -> ""
                    },
                )
            )
        }

    }

    private fun setSpinner() {
        binding.role.setSpinnerEventsListener()

        selectedPeriodic = list[0]
        binding.role.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                selectedPeriodic = list[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun renderAddStaff(data: AddStaffData) {
        Toast.makeText(requireContext(), "Staff Added Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

    private fun showInformationButton(isEnabled: Boolean) {
        binding.create.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        if (view != null) {
            val firstName = binding.firstName.text.isNullOrBlank()
            val lastName = binding.lastName.text.isNullOrBlank()
            val phone = binding.phone.text.isNullOrBlank()
            val address = binding.address.text.isNullOrBlank()
            val userEmail = binding.emailAddress.text.isNullOrBlank()
            val userPassword = binding.password.text?.length!! >= 8
            val userPasswordConfirmation = binding.confirmPassword.text?.length!! >= 8
            showInformationButton(!firstName && !lastName && !phone && !address && !userEmail && userPassword && userPasswordConfirmation)
        }
    }

}




