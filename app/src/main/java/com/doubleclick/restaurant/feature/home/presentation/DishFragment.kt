package com.doubleclick.restaurant.feature.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentDishBinding
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.presentation.adapter.IngredientsAdapter
import com.doubleclick.restaurant.feature.home.presentation.adapter.SizeAdapter
import com.doubleclick.restaurant.utils.Constant.dollarSign
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DishFragment : BaseFragment(R.layout.fragment_dish) {
    private val binding by viewBinding(FragmentDishBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val navArgs: DishFragmentArgs by navArgs()
    private val sizeListAdapter = SizeAdapter()
    private val ingredientListAdapter = IngredientsAdapter()
    private var quantity = 1
    private var pricePerUnit = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.text = navArgs.item.name
        binding.rvSize.adapter = sizeListAdapter
        binding.rvIngredients.adapter = ingredientListAdapter
        sizeListAdapter.submitList(navArgs.item.sizes)
        ingredientListAdapter.submitList(navArgs.item.ingredients)
        binding.price.text = "${dollarSign}${pricePerUnit}"
        if (navArgs.item.ingredients.isNullOrEmpty()) {
            binding.llIngredients.visibility = View.GONE
        } else binding.llIngredients.visibility = View.VISIBLE

        with(viewModel) {
            observe(putCart, ::renderPutCart)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.cart.setOnClickListener {
            findNavController().navigate(R.id.cartFragment)
        }
        sizeListAdapter.clickShowSize = { id, price ->
            pricePerUnit = price
            updateTotalPrice()
            binding.price.text = "${dollarSign}${price}"
            binding.addToCart.setOnClickListener {
                viewModel.putCart(PutCartRequest(quantity.toString(), id, price.toString()))
            }
        }

        binding.quantity.text = quantity.toString()
        binding.plus.setOnClickListener {
            quantity++
            binding.quantity.text = quantity.toString()
            binding.minus.isEnabled = true
            updateTotalPrice()
        }

        binding.minus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.quantity.text = quantity.toString()
                binding.minus.isEnabled = quantity > 1
                updateTotalPrice()
            }
        }
        binding.minus.isEnabled = quantity > 1

    }

    private fun updateTotalPrice() {
        val totalPrice = quantity * pricePerUnit
        binding.totalPrice.text = "${dollarSign}${totalPrice}"
    }

    private fun renderPutCart(data: PutCartResponse) {
        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}



