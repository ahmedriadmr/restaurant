package com.doubleclick.rovleapp.feature.shop.cartDetails.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentFacturaBinding
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.shop.cart.CartViewModel
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails.OrderDetailsData
import com.doubleclick.rovleapp.utils.Constant.euroSign
import com.doubleclick.rovleapp.wallet.activity.CheckoutOrderActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class ShowCartInformationFragment : BaseFragment(R.layout.fragment_factura) {
    private val binding by viewBinding(FragmentFacturaBinding::bind)
    private val viewModel: CartViewModel by viewModels()
    private val navArgs: ShowCartInformationFragmentArgs by navArgs()
    private val orderItemAdapter = OrderItemAdapter()
    private var orderDetailsReturn: OrderDetailsData? = null
    private var totalCost by Delegates.notNull<Double>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.resumen_pedido)
        binding.rvMyPackage.adapter = orderItemAdapter
        binding.llReceipt.visibility = View.GONE
        binding.llStatus.visibility = View.GONE
        binding.llProvider.visibility = View.GONE
        with(viewModel) {
            observeOrNull(orderDetails, ::renderOrderDetails)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getOrderDetails(navArgs.providerId)
        }
        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.confirm.setOnClickListener { renderPaymentMethod() }
    }

    private fun renderPaymentMethod() {
        viewLifecycleOwner.lifecycleScope.launch {
            when (appSettingsSource.getPaymentMethod()) {
                0 -> navigateToPaymentMethod()
                1 -> navigateToPayByVisa()
                2 -> navigateToPayByGooglePay()
            }
        }
    }

    private fun navigateToPaymentMethod() {
        orderDetailsReturn?.let {
            findNavController().navigate(
                ShowCartInformationFragmentDirections.actionShowCartInformationFragmentToPaymentMethodFragment(
                    navArgs.orderRequest, totalCost.toFloat(), it
                )
            )
        }
    }

    private fun navigateToPayByVisa() {
        orderDetailsReturn?.let {
            findNavController().navigate(
                ShowCartInformationFragmentDirections.actionShowCartInformationFragmentToPaymentFragment(navArgs.orderRequest, totalCost.toString())
            )
        }
    }

    private fun navigateToPayByGooglePay() {
        orderDetailsReturn?.let {
            val intent = Intent(requireActivity(), CheckoutOrderActivity::class.java)
            intent.putExtra("orderRequest", navArgs.orderRequest)
                .putExtra("orderDetailsReturn", it)
                .putExtra("totalCost",totalCost)
            startActivity(intent)
        }
    }

    private fun renderOrderDetails(data: OrderDetailsData?) {
        data?.let {
            orderDetailsReturn = data
            orderItemAdapter.submitList(data.cart_items)
            binding.shipping.text = data.shipping?.let { "$it $euroSign" } ?: "0.0 $euroSign"
            binding.totalCost.text = getString(R.string.total_cost_placeholder, data.total_price.toString(), euroSign)
            totalCost = data.total_price

        }

    }

    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)

        binding.header.photo.load(user?.image) {
            scale(Scale.FILL)
            placeholder(R.drawable.image)
            error(R.drawable.image)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val userToken = viewModel.appSettingsSource.user().firstOrNull()?.token

            if (userToken != null && userToken != "-1") {

                binding.header.photo.setOnClickListener { startActivity(Intent(requireActivity(), ProfileActivity::class.java)) }
            } else {
                binding.header.photo.setOnClickListener { startActivity(AuthActivity.callingIntent(requireActivity())) }
            }
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}