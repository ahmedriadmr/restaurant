package com.doubleclick.restaurant.feature.home.data.makeOrder.request

data class ItemRequest(
    val item_id: Int,
    val number: Int,
    val size_name: String,
    val size_price: Double,
    val total: Double
)