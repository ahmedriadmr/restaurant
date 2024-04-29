package com.doubleclick.domain.ts

import com.doubleclick.domain.model.items.get.Item
import com.doubleclick.domain.model.items.get.Size

interface OnAddToCart {
    fun addToCart(item: Item,size: Size)
}