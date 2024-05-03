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
import com.doubleclick.restaurant.databinding.FragmentCartNewBinding
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.presentation.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart_new) {

    private val binding by viewBinding(FragmentCartNewBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val cartAdapter =  CartAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(listCart) { cart -> renderListCart(cart){cartAdapter.submitList(null)}}
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getCart()
        }
        binding.rvCart.adapter = cartAdapter

    }
    private fun renderListCart(cart: List<CartData>, refreshData: (() -> Unit)?) {
        when {
            cart.isEmpty() -> refreshData?.invoke()
            else -> cartAdapter.submitList(cart)
        }
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

    }

