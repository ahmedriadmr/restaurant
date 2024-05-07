package com.doubleclick.restaurant.feature.home.data.makeOrder.request

data class MakeOrderRequest(
    val items: List<ItemRequest>,
    val location: String?,
    val order_type: String,
    val table_number: String?,
    val total: Double,
    val waiter_id: Int?
)