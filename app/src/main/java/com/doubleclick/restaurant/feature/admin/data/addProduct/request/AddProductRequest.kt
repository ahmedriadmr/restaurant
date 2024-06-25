package com.doubleclick.restaurant.feature.admin.data.addProduct.request

data class AddProductRequest(
    val category_id: Int,
    val description: String,
    val ingredients: List<Ingredient>?,
    val name: String,
    val sizes: List<Size>,
    val vip: Int
)