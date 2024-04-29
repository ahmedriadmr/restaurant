package com.doubleclick.rovleapp.feature.shop.cart.usecase

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<Unit, DeleteCartUseCase.Params>() {

    override suspend fun run(params: Params) = repository.deleteCart(params.cartId)

    data class Params(val cartId: Float?)
}