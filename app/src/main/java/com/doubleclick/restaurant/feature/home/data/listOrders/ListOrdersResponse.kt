package com.doubleclick.restaurant.feature.home.data.listOrders

data class ListOrdersResponse(
    val `data`: List<OrdersData>,
    val status: String
)