package com.doubleclick.domain.ts

import com.doubleclick.domain.model.carts.get.CartsModel
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import kotlinx.coroutines.Job

interface OnClickAlert {
    fun dismiss()
    fun onClickOk(cartModel: CartData? = null): Job
}