package com.doubleclick.restaurant.presentation.ui.admin.ui

import android.os.Bundle
import android.view.View
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
import com.doubleclick.restaurant.databinding.FragmentStaffBinding
import com.doubleclick.restaurant.feature.admin.AdminViewModel
import com.doubleclick.restaurant.feature.admin.data.listStaff.UsersData
import com.doubleclick.restaurant.feature.admin.presentation.adapter.StaffAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffFragment : BaseFragment(R.layout.fragment_staff) {
    private val binding by viewBinding(FragmentStaffBinding::bind)
    private val viewModel: AdminViewModel by viewModels()
    private val staffAdapter = StaffAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observe(listUsers) { users -> renderListUsers(users) { staffAdapter.submitList(null) } }
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getUsers()
        }

        binding.rvStaff.adapter = staffAdapter
        binding.add.setOnClickListener {
            findNavController().navigate(StaffFragmentDirections.actionStaffFragmentToAddStaffFragment())
        }


    }


    private fun renderListUsers(items: List<UsersData>, refreshData: (() -> Unit)?) {
        val filteredUsers = items.filter { user ->
            user.roles.any { role -> role.name.equals("waiter", ignoreCase = true) || role.name.equals("chief", ignoreCase = true) }
        }
        when {
            filteredUsers.isEmpty() -> refreshData?.invoke()
            else -> staffAdapter.submitList(filteredUsers)
        }
    }



    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}




