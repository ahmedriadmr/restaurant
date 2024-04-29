package com.doubleclick.domain.model.order.get

data class Data(
    val id: Int,
    val items: List<Item>,
    val location: String,
    val order_type: String,
    val status: String,
    val table_id: Int,
    val total: Int,
    val user_id: Int,
    val waiter_id: Int
)