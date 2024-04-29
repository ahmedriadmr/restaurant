package com.doubleclick.rovleapp.feature.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.databinding.FragmentProductsInOrderBinding
import com.doubleclick.rovleapp.feature.profile.data.orders.showOrder.ShowOrderData
import com.doubleclick.rovleapp.feature.profile.presentation.adapter.ProductInOrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsInOrderFragment : BaseFragment(R.layout.fragment_products_in_order) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding by viewBinding(FragmentProductsInOrderBinding::bind)
    private val adapter = ProductInOrderAdapter()
    private val navArgs: ProductsInOrderFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observeOrNull(showOrder, ::handleShowProductsInOrder)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getShowOrder(navArgs.orderId)
        }

        binding.header.title.text = getString(R.string.products_in_order)
        binding.rvAllProductsInOrder.adapter = adapter

        binding.header.back.setOnClickListener {
            findNavController().popBackStack()
        }

        adapter.clickRateOrder = {  product ->
            findNavController().navigate(
                ProductsInOrderFragmentDirections.actionProductsInOrderFragmentToRatingOrderFragment(product)
            )
        }


    }

    private fun handleShowProductsInOrder(data: ShowOrderData?) {
        data?.let {
            adapter.submitList(data.products)
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}