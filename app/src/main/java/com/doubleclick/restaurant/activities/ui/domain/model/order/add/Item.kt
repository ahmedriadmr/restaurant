package com.doubleclick.domain.model.order.add

data class Item(
    val item_id: Int,
    val number: Int,
    val size_name: String,
    val size_price: Double,
    val total: Double
)