package com.doubleclick.restaurant.feature.shop.cart.usecase

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<Unit, DeleteCartUseCase.Params>() {

    override suspend fun run(params: Params) = repository.deleteCart(params.cartId)

    data class Params(val cartId: Float?)
}