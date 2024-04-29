package com.doubleclick.domain.model.items.get

data class Size(
    val id: Int,
    val item_id: Int,
    val name: String,
    val price: Double,
    val status: String
)