package com.doubleclick.restaurant.feature.shop.payment.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentFacturaBinding
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.profile.data.orders.showOrder.ShowOrderData
import com.doubleclick.restaurant.feature.profile.presentation.ProfileViewModel
import com.doubleclick.restaurant.feature.profile.presentation.adapter.ShowOrderItemAdapter
import com.doubleclick.restaurant.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FacturaAfterPaymentFragment : BaseFragment(R.layout.fragment_factura) {

    private val binding by viewBinding(FragmentFacturaBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private val navArgs: FacturaAfterPaymentFragmentArgs by navArgs()
    private val orderItemAdapter = ShowOrderItemAdapter()
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Navigate back to previous activity
            requireActivity().finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.title.text = getString(R.string.factura)
        binding.llButtonConfirm.visibility = View.GONE
        binding.rvMyPackage.adapter = orderItemAdapter

        with(viewModel) {
            observeOrNull(showOrder, ::handleFactura)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getShowOrder(navArgs.orderId)
        }

        binding.header.back.setOnClickListener {
            // Navigate back to previous activity
            requireActivity().finish()
        }
    }

    private fun handleFactura(data: ShowOrderData?) {
        data?.let {
            orderItemAdapter.submitList(data.products)
            val receiptIdText = getString(R.string.receipt_id) + data.id
            binding.orderId.text = receiptIdText
            binding.status.text = data.status
            binding.providerName.text = data.provider?.commercial_name
            binding.shipping.text = data.shipping?.let { "$it ${Constant.euroSign}" } ?: "0.0 ${Constant.euroSign}"
            val totalCostText = getString(R.string.total_cost_placeholder, data.total, Constant.euroSign)
            binding.totalCost.text = totalCostText
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

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }
}