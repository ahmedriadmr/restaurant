package com.doubleclick.restaurant.feature.shop.cart.locker.data

data class LockerResponse(
    val `data`: List<LockerData>,
    val message: String,
    val status: Int
)