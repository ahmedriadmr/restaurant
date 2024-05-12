package com.doubleclick.restaurant.feature.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.feature.chef.domain.model.OrderState
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.SearchOrdersData
import com.doubleclick.restaurant.feature.home.presentation.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryOrdersFragment : BaseFragment(R.layout.fragment_history_orders) {

    private val binding by viewBinding(com.doubleclick.restaurant.databinding.FragmentHistoryOrdersBinding::bind)
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

    private fun renderListOrders(order: List<SearchOrdersData>, refreshData: (() -> Unit)?) {
        val filteredOrders = order.filter { it.status != OrderState.ONGOING.value }
        when {
            filteredOrders.isEmpty() -> refreshData?.invoke()
            else -> {
                ordersAdapter.submitList(filteredOrders)
            }
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}

