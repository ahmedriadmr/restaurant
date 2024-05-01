package com.doubleclick.domain.ts

import com.doubleclick.domain.model.carts.get.CartsModel
import kotlinx.coroutines.Job

interface OnClickAlert {
    fun dismiss()
    fun onClickOk(cartModel: CartsModel? = null): Job
}