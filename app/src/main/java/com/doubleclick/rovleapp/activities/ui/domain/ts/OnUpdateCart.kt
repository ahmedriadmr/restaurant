package com.doubleclick.domain.ts

import com.doubleclick.domain.model.carts.get.CartsModel
import kotlinx.coroutines.Job

interface OnUpdateCart {
    fun updateItem(input: Int, cartsModel: CartsModel)
}