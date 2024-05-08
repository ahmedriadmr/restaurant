package com.doubleclick.restaurant.feature.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
import com.doubleclick.restaurant.databinding.FragmentCheckoutBinding
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.makeOrder.request.MakeOrderRequest
import com.doubleclick.restaurant.feature.home.data.makeOrder.response.MakeOrderResponse
import com.doubleclick.restaurant.feature.home.presentation.adapter.OrderItemsAdapter
import com.doubleclick.restaurant.utils.Constant.dollarSign
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutFragment : BaseFragment(R.layout.fragment_checkout) {

    private val binding by viewBinding(FragmentCheckoutBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val checkoutAdapter = OrderItemsAdapter()
    private var selectedRadio: Int = -1
    var total:Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(listCart) { cart -> renderListCart(cart){checkoutAdapter.submitList(null)}}
            observe(makeOrder, ::renderMakeOrder)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getCart()
        }
        binding.rvItems.adapter = checkoutAdapter
        checkoutAdapter.cartUpdated = { totalPrice ->
            binding.totalPrice.text = "${dollarSign}$totalPrice"
            total = totalPrice.toDouble()
        }
        onClick()
    }



    private fun onClick() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.restaurant -> {
                    selectedRadio = R.id.restaurant
                    checkEnableShopInformationButton()
                    binding.tableNumber.visibility = View.VISIBLE
                    binding.location.visibility = View.GONE
                    binding.phoneNumber.visibility = View.GONE

                }

                R.id.delivery -> {
                    selectedRadio = R.id.delivery
                    checkEnableShopInformationButton()
                    binding.tableNumber.visibility = View.GONE
                    binding.location.visibility = View.VISIBLE
                    binding.phoneNumber.visibility = View.VISIBLE

                }

                R.id.take_away -> {
                    selectedRadio = R.id.take_away
                    checkEnableShopInformationButton()
                    binding.tableNumber.visibility = View.GONE
                    binding.location.visibility = View.GONE
                    binding.phoneNumber.visibility = View.VISIBLE

                }
            }
        }
        binding.tableNumber.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.location.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.phoneNumber.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.confirm.setOnClickListener {
            viewModel.makeOrder(MakeOrderRequest(checkoutAdapter.getConfirmedList()!!,
                when(selectedRadio){
                    R.id.restaurant -> null
                    R.id.take_away -> null
                    R.id.delivery -> binding.location.text.toString()
                    else -> null
                },
                when(selectedRadio){
                    R.id.restaurant -> "Restaurant"
                    R.id.take_away -> "Take_away"
                    R.id.delivery -> "Delivery"
                    else -> ""
                },
                when(selectedRadio){
                    R.id.restaurant -> binding.tableNumber.text.toString()
                    R.id.take_away -> null
                    R.id.delivery -> null
                    else -> {null}
                },
                total, null))
        }
    }




    private fun renderListCart(cart: List<CartData>, refreshData: (() -> Unit)?) {
        when {
            cart.isEmpty() -> refreshData?.invoke()
            else -> {
                checkoutAdapter.submitList(cart)
            }
        }
    }

    private fun renderMakeOrder( data: MakeOrderResponse) {
        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

    private fun showInformationButton(isEnabled: Boolean) {
        binding.confirm.isEnabled = isEnabled
        if (isEnabled) {
            binding.confirm.setBackgroundResource(R.drawable.bg_black_rec_fill)
        } else {
            binding.confirm.setBackgroundResource(R.drawable.bg_gray_fill)
        }
    }

    private fun checkEnableShopInformationButton() {
        val isConfirmEnabled = when (selectedRadio) {
            R.id.restaurant -> {
                binding.tableNumber.text.isNotBlank()
            }
            R.id.delivery -> {
                binding.location.text.isNotBlank() && binding.phoneNumber.text.isNotBlank()
            }
            R.id.take_away -> {
                binding.phoneNumber.text.isNotBlank()
            }
            else -> false
        }
        showInformationButton(isConfirmEnabled)
    }


}
