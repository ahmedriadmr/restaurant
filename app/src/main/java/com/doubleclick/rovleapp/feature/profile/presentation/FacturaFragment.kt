package com.doubleclick.rovleapp.feature.profile.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
import com.doubleclick.rovleapp.core.platform.BaseDialogFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentFacturaBinding
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.profile.data.orders.showOrder.ShowOrderData
import com.doubleclick.rovleapp.feature.profile.presentation.adapter.ShowOrderItemAdapter
import com.doubleclick.rovleapp.utils.Constant.euroSign
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FacturaFragment : BaseDialogFragment(R.layout.fragment_factura) {

    private val binding by viewBinding(FragmentFacturaBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private val navArgs: FacturaFragmentArgs by navArgs()
    private val orderItemAdapter = ShowOrderItemAdapter()
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
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun handleFactura(data: ShowOrderData?) {
        data?.let {
            orderItemAdapter.submitList(data.products)
            val receiptIdText = getString(R.string.receipt_id) + data.id
            binding.orderId.text = receiptIdText
            binding.status.text = data.status
            binding.providerName.text = data.provider?.commercial_name
            binding.shipping.text = data.shipping?.let { "$it $euroSign" } ?: "0.0 $euroSign"
            val totalCostText = getString(R.string.total_cost_placeholder, data.total, euroSign)
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
}