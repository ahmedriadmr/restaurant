package com.doubleclick.restaurant.feature.subscriptions.paymentSubscription

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseDialogFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentPaymentBinding
import com.doubleclick.restaurant.dialog.DialogPaymentSuccess
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.response.PaySubscriptionData
import com.doubleclick.restaurant.utils.Constant.euroSign
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentSubscriptionFragment : BaseDialogFragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private val viewModel: PaySubscriptionViewModel by viewModels()
    private val navArgs: PaymentSubscriptionFragmentArgs by navArgs()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.pagos)
        binding.totalCost.text = getString(R.string.total_cost_placeholder, navArgs.totalCost, euroSign)
        with(viewModel) {
            observe(paySubscription, ::handlePaySubscription)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }
        binding.header.back.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }


        binding.payNow.setOnClickListener {
            val cardName = binding.cardName.text.toString()
            if (cardName.isBlank()) {
                val inflater = requireActivity().layoutInflater
                val layout: View = inflater.inflate(R.layout.custom_toast,
                    requireActivity().findViewById(R.id.custom_toast_container))

// Set the text of the TextView in the custom layout
                val text: TextView = layout.findViewById(R.id.text)
                text.text = getString(R.string.card_name_not_empty)

// Create and show the custom toast
                val toast = Toast(requireContext())
                toast.duration = Toast.LENGTH_SHORT
                toast.view = layout
                toast.show()
                return@setOnClickListener
            }
            try {
                val subscriptionId = navArgs.subscriptionId

                val cardNumberString = binding.cardNumber.text.toString()
                val convertedCreditCard = validateCardNumberAndConvert(cardNumberString)

                val expiryDateString = binding.cardExpiryDate.text.toString()
                if (expiryDateString.isBlank()) {
                    // Handle empty expiry date field
                    val inflater = requireActivity().layoutInflater
                    val layout: View = inflater.inflate(R.layout.custom_toast,
                        requireActivity().findViewById(R.id.custom_toast_container))

// Set the text of the TextView in the custom layout
                    val text: TextView = layout.findViewById(R.id.text)
                    text.text = getString(R.string.expiry_date_not_empty)

// Create and show the custom toast
                    val toast = Toast(requireContext())
                    toast.duration = Toast.LENGTH_SHORT
                    toast.view = layout
                    toast.show()
                    throw IllegalArgumentException("Expiry date cannot be empty")
                }
                val convertedExpiryDate = validateCardExpiryDateAndConvert(expiryDateString)

                val cardCvcString = binding.cardCvc.text.toString()
               
                // Call the viewModel method with validated inputs
                viewModel.doPaySubscription(subscriptionId, convertedCreditCard.toString(), convertedExpiryDate, cardCvcString)

            } catch (e: NumberFormatException) {
                // Handle conversion errors or other exceptions
                e.printStackTrace()
                // Display an error message or take appropriate action
            } catch (e: IllegalArgumentException) {
                // Handle empty expiry date field
                e.printStackTrace()
                // Display an error message or take appropriate action
            }
        }

    }

    private fun validateCardNumberAndConvert(cardNumberString: String): Long {
        val sanitizedCardNumber = cardNumberString.replace("-", "")
        if (sanitizedCardNumber.matches("\\d{16}".toRegex())) {
            return sanitizedCardNumber.toLong()
        } else {
            val inflater = requireActivity().layoutInflater
            val layout: View = inflater.inflate(R.layout.custom_toast,
                requireActivity().findViewById(R.id.custom_toast_container))

// Set the text of the TextView in the custom layout
            val text: TextView = layout.findViewById(R.id.text)
            text.text = getString(R.string.invalid_card_number)

// Create and show the custom toast
            val toast = Toast(requireContext())
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.show()
            throw NumberFormatException("Invalid card number format")
        }
    }
    private fun validateCardExpiryDateAndConvert(expiryDateString: String): String {
        val sanitizedExpiryDate = expiryDateString.replace("/", "")
        if (sanitizedExpiryDate.matches("\\d{4}".toRegex())) {
            return sanitizedExpiryDate
        } else {
            val inflater = requireActivity().layoutInflater
            val layout: View = inflater.inflate(R.layout.custom_toast,
                requireActivity().findViewById(R.id.custom_toast_container))

// Set the text of the TextView in the custom layout
            val text: TextView = layout.findViewById(R.id.text)
            text.text = getString(R.string.invalid_expiry_date)

// Create and show the custom toast
            val toast = Toast(requireContext())
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.show()
            throw IllegalArgumentException("Invalid expiry date format")
        }
    }
    private fun handlePaySubscription(@Suppress("UNUSED_PARAMETER") data: PaySubscriptionData){

        val dialog = DialogPaymentSuccess(requireActivity())
        dialog.findViewById<Button>(R.id.terminar)?.apply {
            text = getString(R.string.suscripcion_completed)
            setOnClickListener {
                dialog.dismiss()
                requireActivity().finish()
            }
        }
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
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

    override fun renderFailure(message: String?) {
        Toast.makeText(requireContext(), "Los datos de latarjeta parecen ser erroneos, por favor reviselos.", Toast.LENGTH_SHORT).show()
    }

    override fun renderFeatureFailure(message: String?) {
        Toast.makeText(requireContext(), "Los datos de latarjeta parecen ser erroneos, por favor reviselos.", Toast.LENGTH_SHORT).show()
    }
}