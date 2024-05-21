package com.doubleclick.restaurant.feature.admin.data.addProduct.response

data class Item(
    val category_id: Int,
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val vip: Int
)