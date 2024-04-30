package com.doubleclick.restaurant.feature.profile.data.orders.showOrder

data class ShowOrderResponse(
    val `data`: ShowOrderData,
    val message: String,
    val status: Int
)