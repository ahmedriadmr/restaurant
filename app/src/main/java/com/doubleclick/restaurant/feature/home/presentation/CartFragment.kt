package com.doubleclick.restaurant.feature.home.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.domain.ts.OnClickAlert
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentCartNewBinding
import com.doubleclick.restaurant.dialog.dialog.AlertDeleteDialog
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.home.data.updateCart.response.UpdateCartResponse
import com.doubleclick.restaurant.feature.home.presentation.adapter.CartAdapter
import com.doubleclick.restaurant.utils.Constant.dollarSign
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart_new), OnClickAlert {

    private val binding by viewBinding(FragmentCartNewBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val cartAdapter = CartAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(listCart) { cart -> renderListCart(cart) { cartAdapter.submitList(null) } }
            observe(updateCart, ::renderUpdateCart)
            observe(deleteCart, ::renderDeleteCart)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getCart()
        }
        binding.rvCart.adapter = cartAdapter
        cartAdapter.cartUpdated = { totalPrice ->
            binding.totalPrice.text = "$dollarSign$totalPrice"
        }
        cartAdapter.clickUpdateCart = { id, number, sizeId ->
            viewModel.updateCart(id, UpdateCartRequest("PUT", number, sizeId))
        }

        binding.checkout.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckoutFragment())
        }

        cartAdapter.onActionClicked { contact, action ->
            when (action.actionId) {
                R.id.delete -> {
                    val dialog = AlertDeleteDialog(requireActivity())
                    dialog.show()
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.findViewById<TextView>(R.id.cancel)?.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.findViewById<TextView>(R.id.remove)?.setOnClickListener {

                        viewModel.deleteCart(contact.id)


                        dialog.dismiss()
                    }


                }
            }
        }

    }

    private fun renderListCart(cart: List<CartData>, refreshData: (() -> Unit)?) {
        when {
            cart.isEmpty() -> refreshData?.invoke()
            else -> {
                cartAdapter.submitList(cart)
            }
        }
    }

    private fun renderUpdateCart(data: UpdateCartResponse) {
        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
        viewModel.getCart()
    }

    private fun renderDeleteCart(data: PutCartResponse) {
        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
        viewModel.getCart()
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

    override fun dismiss() {
        //nothing
    }

    override fun onClickOk(cartModel: CartData?): Job = lifecycleScope.launch {
        //here you can call api fro delete
    }

}

