package com.doubleclick.rovleapp.feature.shop.cart.usecase

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.cart.response.getCart.CartData
import javax.inject.Inject

class GetCartUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<CartData, GetCartUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getCart(params.page)
    data class Params(val page: Int)
}