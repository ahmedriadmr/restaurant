package com.doubleclick.domain.model.category.get

data class Item(
    val category_id: Int,
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val vip: String
)