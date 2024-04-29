package com.doubleclick.domain.model.items.get

data class Item(
    val category: Category,
    val category_id: Int,
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val sizes: List<Size>,
    val status: String,
    val vip: String
)