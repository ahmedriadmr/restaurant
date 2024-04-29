package com.doubleclick.domain.model.order.get

data class Item(
    val id: Int,
    val item_id: Int,
    val number: Int,
    val order_id: Int,
    val size_name: String,
    val size_price: Int,
    val total: Int,
    val user_id: Int
)