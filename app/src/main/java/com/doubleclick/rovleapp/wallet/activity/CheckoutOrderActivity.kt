package com.doubleclick.restaurant.wallet.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.platform.BaseActivity
import com.doubleclick.restaurant.databinding.ActivityCheckoutBinding
import com.doubleclick.restaurant.feature.shop.cartDetails.data.makeOrder.request.MakeOrderRequest
import com.doubleclick.restaurant.feature.shop.cartDetails.data.orderDetails.OrderDetailsData
import com.doubleclick.restaurant.feature.shop.payment.data.googlePay.request.PayOrderGooglePayRequest
import com.doubleclick.restaurant.feature.shop.payment.data.googlePay.response.PayOrderGooglePayData
import com.doubleclick.restaurant.feature.shop.payment.presentation.PayOrderViewModel
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.googlePay.request.PaymentDetails
import com.doubleclick.restaurant.wallet.util.PaymentsUtil
import com.doubleclick.restaurant.wallet.viewmodel.CheckoutViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.pay.PayClient
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.button.ButtonOptions
import com.google.android.gms.wallet.button.PayButton
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject

@AndroidEntryPoint
class CheckoutOrderActivity : BaseActivity() {

    private val addToGoogleWalletRequestCode = 1000

    private val model: CheckoutViewModel by viewModels()
    private val viewModel: PayOrderViewModel by viewModels()
    lateinit var binding: ActivityCheckoutBinding
    private lateinit var googlePayButton: PayButton
    private lateinit var addToGoogleWalletButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observe(payOrderGooglePay, ::renderPayOrderGooglePay)
//            doPayOrderGooglePay(handlePayOrderGooglePayRequest())
        }

        // Use view binding to access the UI elements
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup buttons
        googlePayButton = binding.googlePayButton
        googlePayButton.initialize(
            ButtonOptions.newBuilder()
                .setAllowedPaymentMethods(PaymentsUtil.allowedPaymentMethods.toString()).build()
        )
        googlePayButton.setOnClickListener { requestPayment() }

        addToGoogleWalletButton = binding.addToGoogleWalletButton.root
        addToGoogleWalletButton.setOnClickListener { requestSavePass() }

        // Check Google Pay availability
        model.canUseGooglePay.observe(this, Observer(::setGooglePayAvailable))
        model.canSavePasses.observe(this, Observer(::setAddToGoogleWalletAvailable))
    }

    /**
     * If isReadyToPay returned `true`, show the button and hide the "checking" text. Otherwise,
     * notify the user that Google Pay is not available. Please adjust to fit in with your current
     * user flow. You are not required to explicitly let the user know if isReadyToPay returns `false`.
     *
     * @param available isReadyToPay API response.
     */
    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            googlePayButton.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "google_pay_status_unavailable",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * If the Google Wallet API is available, show the button to Add to Google Wallet. Please adjust to fit
     * in with your current user flow.
     *
     * @param available
     */
    private fun setAddToGoogleWalletAvailable(available: Boolean) {
        if (available) {
            binding.passContainer.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "google_wallet_status_unavailable",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun requestPayment() {

        // Disables the button to prevent multiple clicks.
        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        val dummyPriceCents = 100L
        val shippingCostCents = 900L
        val totalCost: Float = intent.getFloatExtra("totalCost", 0.0f)
        val task = totalCost.let { model.getLoadPaymentDataTask(it.toLong()) }

        task.addOnCompleteListener { completedTask ->
            if (completedTask.isSuccessful) {
                completedTask.result.let(::handlePaymentSuccess)
            } else {
                when (val exception = completedTask.exception) {
                    is ResolvableApiException -> {
                        resolvePaymentForResult.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    }

                    is ApiException -> {
                        handleError(exception.statusCode, exception.message)
                    }

                    else -> {
                        handleError(
                            CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                                    " exception when trying to deliver the task result to an activity!"
                        )
                    }
                }
            }

            // Re-enables the Google Pay payment button.
            googlePayButton.isClickable = true
        }
    }

    // Handle potential conflict from calling loadPaymentData
    private val resolvePaymentForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK ->
                    result.data?.let { intent ->
                        PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                    }

                RESULT_CANCELED -> {
                    // The user cancelled the payment attempt
                }
            }
        }

    private fun renderPayOrderGooglePay(data: PayOrderGooglePayData) {
//        startActivity(Intent(this, CheckoutSuccessActivity::class.java))
    }

    /**
     * PaymentData response object contains the payment information, as well as any additional
     * requested information, such as billing and shipping address.
     *
     * @param paymentData A response object returned by Google after a payer approves payment.
     * @see [Payment
     * Data](https://developers.google.com/pay/api/android/reference/object.PaymentData)
     */
    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson()

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")
            Log.d("BillingName", billingName)

            Toast.makeText(
                this,
                "payments_show_name " + billingName,
                Toast.LENGTH_LONG
            ).show()

            // Logging token string.
            Log.d(
                "Google Pay token", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
            )
            viewModel.doPayOrderGooglePay(handlePayOrderGooglePayRequest())

        } catch (error: JSONException) {
            Log.e("handlePaymentSuccess", "Error: $error")
        }
    }

    private fun handlePayOrderGooglePayRequest(): PayOrderGooglePayRequest {
        val orderRequest: MakeOrderRequest? = intent.getParcelableExtra("orderRequest")
        val orderDetailsReturn: OrderDetailsData? = intent.getParcelableExtra("orderDetailsReturn")
        if (orderRequest != null && orderDetailsReturn != null) {
            // Map cart items to update passport_offer_id if it's null
            val updatedCartItems = orderDetailsReturn.cart_items.map { cartItem ->
                val updatedPassportOfferId = cartItem.passport_offer_id ?: ""
                cartItem.copy(passport_offer_id = updatedPassportOfferId)
            }
            return PayOrderGooglePayRequest(
                orderRequest.provider_id,
                orderRequest.delivery_type,
                orderRequest.zip_code,
                orderRequest.name,
                orderRequest.phone,
                orderRequest.email,
                orderRequest.address,
                orderRequest.coffee_shop_id,
                orderRequest.locker_location,
                orderRequest.note,
                updatedCartItems, // Use updated cart items with updated passport_offer_id
                PaymentDetails(
                    orderDetailsReturn.total_price, orderDetailsReturn.shipping?: 0.0, orderDetailsReturn.taxes?: 0.0, "test1234", "google_pay", "success", "succeeded"
                ))
        } else {
            // Return a default PayOrderGooglePayRequest if conditions are not met
            return PayOrderGooglePayRequest(
                "", "", "", "", "", "", "", "", "", "", emptyList(), PaymentDetails.empty
            )
        }
    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     * WalletConstants.ERROR_CODE_* constants.
     * @see [
     * Wallet Constants Library](https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants.constant-summary)
     */
    private fun handleError(statusCode: Int, message: String?) {
        Log.e("Google Pay API error", "Error code: $statusCode, Message: $message")
    }

    private fun requestSavePass() {

        // Disables the button to prevent multiple clicks.
        addToGoogleWalletButton.isClickable = false

        model.savePassesJwt(model.genericObjectJwt, this, addToGoogleWalletRequestCode)
    }

    @Deprecated("Deprecated and in use by Google Pay")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addToGoogleWalletRequestCode) {
            when (resultCode) {
                RESULT_OK -> Toast
                    .makeText(
                        this,
                        "add_google_wallet_success",
                        Toast.LENGTH_LONG
                    )
                    .show()

                RESULT_CANCELED -> {
                    // Save canceled
                }

                PayClient.SavePassesResult.SAVE_ERROR -> data?.let { intentData ->
                    val apiErrorMessage =
                        intentData.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE)
                    handleError(resultCode, apiErrorMessage)
                }

                else -> handleError(
                    CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                            " exception when trying to deliver the task result to an activity!"
                )
            }

            // Re-enables the Google Pay payment button.
            addToGoogleWalletButton.isClickable = true

        }
    }
}
