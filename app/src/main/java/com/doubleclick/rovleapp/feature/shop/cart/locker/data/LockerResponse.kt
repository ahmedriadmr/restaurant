package com.doubleclick.rovleapp.feature.shop.cart.locker.data

data class LockerResponse(
    val `data`: List<LockerData>,
    val message: String,
    val status: Int
)