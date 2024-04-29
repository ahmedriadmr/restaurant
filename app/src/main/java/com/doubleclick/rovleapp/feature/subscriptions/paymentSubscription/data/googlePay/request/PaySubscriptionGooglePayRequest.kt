package com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaySubscriptionGooglePayRequest (
    val provider_id: String,
    val verification_type: String,
    val subscription_id: String,
    val payment_details: PaymentDetails,
) : Parcelable