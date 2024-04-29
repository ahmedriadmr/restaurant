package com.doubleclick.domain.model.carts.get

data class Carts(
    val `data`: List<CartsModel>,
    val status: String
)