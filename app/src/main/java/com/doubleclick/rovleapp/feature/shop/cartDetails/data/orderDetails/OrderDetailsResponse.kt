package com.doubleclick.restaurant.feature.shop.cartDetails.data.orderDetails

data class OrderDetailsResponse(
    val `data`: OrderDetailsData,
    val message: String,
    val status: Int
)