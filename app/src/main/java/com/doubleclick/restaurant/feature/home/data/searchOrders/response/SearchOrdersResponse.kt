package com.doubleclick.restaurant.feature.home.data.searchOrders.response

data class SearchOrdersResponse(
    val `data`: List<SearchOrdersData>,
    val status: String
)