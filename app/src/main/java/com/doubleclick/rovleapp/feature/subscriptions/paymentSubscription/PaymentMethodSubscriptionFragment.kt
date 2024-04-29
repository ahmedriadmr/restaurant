package com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription

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
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.platform.BaseDialogFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentPaymentMethodBinding
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.utils.Constant.euroSign
import com.doubleclick.rovleapp.wallet.activity.CheckoutSubscriptionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentMethodSubscriptionFragment : BaseDialogFragment(R.layout.fragment_payment_method) {


    private val binding by viewBinding(FragmentPaymentMethodBinding::bind)
    private val viewModel: PaySubscriptionViewModel by viewModels()
    private val navArgs: PaymentMethodSubscriptionFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.pagos)
        binding.totalCost.text = getString(R.string.total_cost_placeholder, navArgs.totalCost.toString(), euroSign)

        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.byVisa.setOnClickListener {
            findNavController().navigate(PaymentMethodSubscriptionFragmentDirections.actionPaymentMethodSubscriptionFragmentToPaymentSubscriptionFragment(navArgs.subscriptionId,navArgs.totalCost.toString()))
        }

        binding.byGoogle.setOnClickListener {

            val intent = Intent(requireActivity(), CheckoutSubscriptionActivity::class.java)
            intent.putExtra("totalCost", navArgs.totalCost)
                .putExtra("subscriptionDetails", navArgs.subscriptionDetails)
            startActivity(intent)

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
}