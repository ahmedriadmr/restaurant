package com.doubleclick.domain.model.order.add

data class Order(
    val items: List<Item>,
    val total: Int
)