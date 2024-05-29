package com.doubleclick.restaurant.presentation.ui.admin.ui

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
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
import com.doubleclick.restaurant.databinding.FragmentListBinding
import com.doubleclick.restaurant.feature.admin.AdminViewModel
import com.doubleclick.restaurant.feature.admin.data.listItems.ItemsData
import com.doubleclick.restaurant.feature.admin.presentation.adapter.ItemsAdapter
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment(R.layout.fragment_list) {
    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: AdminViewModel by viewModels()
    private val itemAdapter = ItemsAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observe(listItems) { items -> renderListItems(items) { itemAdapter.submitList(null) } }
            observe(logout, ::renderLogout)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getItems()
        }

        binding.rvDish.adapter = itemAdapter
        binding.add.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToAddProductFragment())
        }
        binding.logout.setOnClickListener {
            viewModel.doLogout()
        }

    }


    private fun renderListItems(items: List<ItemsData>, refreshData: (() -> Unit)?) {
        when {
            items.isEmpty() -> refreshData?.invoke()
            else -> itemAdapter.submitList(items)
        }
    }
    private fun renderLogout(@Suppress("UNUSED_PARAMETER") data: LogoutResponse) {
        ActivityCompat.finishAffinity(requireActivity())
        navigator.showAuth(requireActivity())
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}




