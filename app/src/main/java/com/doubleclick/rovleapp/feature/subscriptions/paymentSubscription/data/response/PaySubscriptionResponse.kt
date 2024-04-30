package com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.response

data class PaySubscriptionResponse(
    val `data`: PaySubscriptionData,
    val message: String,
    val status: Int
)