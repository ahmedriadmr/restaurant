package com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.response

data class paySubscriptionGooglePayResponse(
    val `data`: PaySubscriptionGooglePayData,
    val message: String,
    val status: Int
)