package com.doubleclick.rovleapp.feature.shop.cart.usecase

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.cart.response.updateCart.UpdateCart
import com.doubleclick.rovleapp.feature.shop.cart.response.updateCart.request.UpdateCartRequest
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<UpdateCart, UpdateCartUseCase.Params>() {

    override suspend fun run(params: Params) =
        repository.updateCart(params.cartId, params.request)

    data class Params(val cartId: Float?, val request: UpdateCartRequest)
}