package com.doubleclick.restaurant.feature.shop.cartDetails.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.cart.response.getCart.NewCart
import javax.inject.Inject

class ShowCartUseCase @Inject constructor(private val showCartRepository: ShopRepository) :
    UseCase<NewCart, ShowCartUseCase.Params>() {

    override suspend fun run(params: Params) = showCartRepository.showCart(params.id)

    data class Params(val id: String)

}