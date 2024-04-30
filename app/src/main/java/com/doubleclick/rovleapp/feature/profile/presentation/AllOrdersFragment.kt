package com.doubleclick.restaurant.feature.profile.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentYourorderBinding
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.profile.data.orders.listOrders.Order
import com.doubleclick.restaurant.feature.profile.presentation.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllOrdersFragment : BaseFragment(R.layout.fragment_yourorder) {

    private val binding by viewBinding(FragmentYourorderBinding::bind)
    private val allOrdersAdapter = OrdersAdapter(true)
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        with(viewModel) {
            observe(listOrders) { orders -> handleListOrdersResponse(orders){allOrdersAdapter.submitList(null) }

            }
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            listOrders()
        }

        binding.rvAllOrders.adapter = allOrdersAdapter
        binding.rvAllOrders.addOnScrollListener(ordersScrollListener)
        binding.header.title.text = getString(R.string.your_orders)


        allOrdersAdapter.clickDetails = { id ->
            navController.navigate(
                AllOrdersFragmentDirections.actionYourOrdersFragmentToFacturaFragment(id)
            )
        }

        allOrdersAdapter.clickRateOrder = {  order ->
            navController.navigate(
                AllOrdersFragmentDirections.actionYourOrdersFragmentToProductsInOrderFragment(order)
            )
        }


        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun handleListOrdersResponse(orders: List<Order>, refreshData: (() -> Unit)?) {
        when {
            orders.isEmpty() -> refreshData?.invoke()
            else -> allOrdersAdapter.submitList(orders)
        }
    }

    var ordersIsLoading = false
    var ordersIsScrolling = false


    private val ordersScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val ordersLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val ordersFirstVisibleItemPosition = ordersLayoutManager.findFirstVisibleItemPosition()
            val ordersVisibleItemCount = ordersLayoutManager.childCount
            val ordersTotalItemCount = ordersLayoutManager.itemCount

            val ordersIsNotLoadingAndNotLastPage = !ordersIsLoading && !viewModel.ordersIsLastPage
            val ordersIsAtLastItem =
                ordersFirstVisibleItemPosition + ordersVisibleItemCount >= ordersTotalItemCount
            val ordersIsNotAtBeginning = ordersFirstVisibleItemPosition >= 0
            val ordersIsTotalMoreThanVisible =
                ordersTotalItemCount >= viewModel.ordersQUERYPAGESIZE
            val ordersShouldPaginate =
                ordersIsNotLoadingAndNotLastPage && ordersIsAtLastItem && ordersIsNotAtBeginning && ordersIsTotalMoreThanVisible && ordersIsScrolling
            if (ordersShouldPaginate) {
                viewModel.listOrders()
                ordersIsScrolling = false
            } else {
                binding.rvAllOrders.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                ordersIsScrolling = true
            }
        }
    }



    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)

        binding.header.photo.load(user?.image) {
            scale(Scale.FILL)
            placeholder(R.drawable.image)
            error(R.drawable.image)
        }
        
        binding.header.photo.setOnClickListener { startActivity(Intent(requireActivity(), ProfileActivity::class.java)) }
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}