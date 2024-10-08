package com.doubleclick.restaurant.feature.home.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.dialog.dialog.AlertDialogCancelOrder
import com.doubleclick.restaurant.feature.chef.domain.model.OrderState
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderRequest
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderResponse
import com.doubleclick.restaurant.feature.home.data.searchOrders.request.SearchOrdersRequest
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.SearchOrdersData
import com.doubleclick.restaurant.feature.home.presentation.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOrdersFragment : BaseFragment(R.layout.fragment_list_orders) {

    private val binding by viewBinding(com.doubleclick.restaurant.databinding.FragmentListOrdersBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val ordersAdapter = OrdersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(searchOrders) { searchOrders -> renderSearchOrders(searchOrders) { ordersAdapter.submitList(null) } }
            observe(cancelOrder, ::renderCancelOrder)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            searchOrders(SearchOrdersRequest(OrderState.ONGOING.value))
        }
        binding.rvMyOrders.adapter = ordersAdapter
        ordersAdapter.clickCancelOrder = { id ->
            val dialog = AlertDialogCancelOrder(requireActivity())
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.findViewById<TextView>(R.id.dismiss)?.setOnClickListener {
                dialog.dismiss()
            }

            dialog.findViewById<TextView>(R.id.cancel)?.setOnClickListener {

                viewModel.cancelOrder(id, CancelOrderRequest("PUT", OrderState.CANCELED.value))
                dialog.dismiss()
            }


        }


    }

    private fun renderSearchOrders(order: List<SearchOrdersData>, refreshData: (() -> Unit)?) {
        when {
            order.isEmpty() -> {
                refreshData?.invoke()
                binding.rvMyOrders.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
                binding.imageEmpty.setImageDrawable(
                    context?.let { ContextCompat.getDrawable(it, R.drawable.group_95) }
                )
            }
            else -> {
                ordersAdapter.submitList(order)
            }
        }
    }

    private fun renderCancelOrder(data: CancelOrderResponse) {
        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
        viewModel.searchOrders(SearchOrdersRequest(OrderState.ONGOING.value))
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}

