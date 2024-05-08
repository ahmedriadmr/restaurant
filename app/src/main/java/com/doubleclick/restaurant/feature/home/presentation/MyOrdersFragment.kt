package com.doubleclick.restaurant.feature.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.listOrders.OrdersData
import com.doubleclick.restaurant.feature.home.data.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.home.data.updateCart.response.UpdateCartResponse
import com.doubleclick.restaurant.feature.home.presentation.adapter.OrdersAdapter
import com.doubleclick.restaurant.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment(R.layout.fragment_my_orders) {

    private val binding by viewBinding(com.doubleclick.restaurant.databinding.FragmentMyOrdersBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val ordersAdapter = OrdersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(listOrders) { order -> renderListOrders(order) { ordersAdapter.submitList(null) } }
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getOrders()
        }
        binding.rvMyOrders.adapter = ordersAdapter


    }

    private fun renderListOrders(order: List<OrdersData>, refreshData: (() -> Unit)?) {
        when {
            order.isEmpty() -> refreshData?.invoke()
            else -> {
                ordersAdapter.submitList(order)
            }
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}

