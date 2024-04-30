package com.doubleclick.restaurant.feature.shop.cart.request.putCart

data class PresentationRequest(
    val id: String,
    var units: Int,
    val productId: Int,
)