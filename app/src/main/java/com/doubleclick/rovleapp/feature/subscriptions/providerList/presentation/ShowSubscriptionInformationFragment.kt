package com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.adapter.spinner.SpinnerAdapter
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseDialogFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentShowInformationBinding
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.presentation.ShowSubscriptionViewModel
import com.doubleclick.rovleapp.utils.Constant.euroSign
import com.doubleclick.rovleapp.wallet.activity.CheckoutSubscriptionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class ShowSubscriptionInformationFragment : BaseDialogFragment(R.layout.fragment_show_information) {

    private val binding by viewBinding(FragmentShowInformationBinding::bind)

    private val viewModel: ShowSubscriptionViewModel by viewModels()
    private val navArgs: ShowSubscriptionInformationFragmentArgs by navArgs()
    private var subscriptionDetailsReturn: SubscriptionData? = null
    private var totalCost by Delegates.notNull<Double>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.nombre_del_plan)
        binding.header.back.setOnClickListener { dismiss() }
        with(viewModel) {
            observeOrNull(showSubscription, ::renderSubscription)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getShowSubscription(navArgs.subscriptionId)
        }
        binding.confirmInformation.setOnClickListener {
            renderPaymentMethod()
        }

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
        subscriptionDetailsReturn?.let{
            findNavController().navigate(
                ShowSubscriptionInformationFragmentDirections.actionShowInformationFragmentToPaymentMethodSubscriptionFragment(
                    navArgs.subscriptionId, totalCost.toFloat(), it
                )
            )
        }
    }

    private fun navigateToPayByVisa() {
        subscriptionDetailsReturn?.let{
            findNavController().navigate(
                ShowSubscriptionInformationFragmentDirections.actionShowInformationFragmentToPaymentSubscriptionFragment(navArgs.subscriptionId,totalCost.toString())
            )
        }
    }

    private fun navigateToPayByGooglePay() {
        subscriptionDetailsReturn?.let{
            val intent = Intent(requireActivity(), CheckoutSubscriptionActivity::class.java)
            intent.putExtra("totalCost", totalCost)
                .putExtra("subscriptionDetails", it)
            startActivity(intent)
        }
    }

    private fun renderSubscription(data: SubscriptionData?) {
        data?.let { subscription ->
            setSpinner(subscription)
            subscriptionDetailsReturn = data
            binding.receiptId.text = getString(R.string.receipt_id_text, subscription.id)
            binding.planName.text = subscription.plan?.name
            binding.providerName.text = subscription.plan?.provider?.commercial_name
            binding.price.text = getString(R.string.total_cost_placeholder, subscription.price, euroSign)
            binding.shipping.text = getString(R.string.total_cost_placeholder, subscription.shipping ?: "0.0", euroSign)
            binding.totalCost.text = getString(R.string.total_cost_placeholder, subscription.total, euroSign)
            totalCost= data.total?.toDouble()?:0.0
        }
    }

    private fun setSpinner(subscription: SubscriptionData) {
        val list = listOf(
            when (subscription.periodicity) {
                1 -> "1 Month"
                3 -> "3 Months"
                else -> "6 Months"
            }
        )
        binding.spinnerPeriod.adapter = SpinnerAdapter(
            requireActivity(),
            list,
            R.color.white,
            Gravity.CENTER_VERTICAL
        )
        binding.spinnerPeriod.setSpinnerEventsListener()
        binding.spinnerPeriod.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    list[i]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    list[0]

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