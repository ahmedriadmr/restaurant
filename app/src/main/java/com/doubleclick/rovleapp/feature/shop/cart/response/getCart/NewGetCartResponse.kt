package com.doubleclick.restaurant.feature.shop.cart.response.getCart

data class NewGetCartResponse(
    val `data`: CartData,
    val message: String,
    val status: Int
)