package com.doubleclick.domain.model.items.add

import com.doubleclick.domain.model.sizes.Sizes

data class Item(
    val name: String,
    val description: String,
    val item_image: String,
    val category_id: Int,
    val vip: Int,
    val sizes: List<Sizes>
)
