package com.doubleclick.restaurant.feature.admin.data.listItems

data class ListItemsResponse(
    val `data`: List<ItemsData>,
    val status: String
)