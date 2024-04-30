package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.listSubscriptions

data class ListMySubscriptionsResponse(
    val `data`: List<MySubscriptionsData>,
    val message: String,
    val status: String
)