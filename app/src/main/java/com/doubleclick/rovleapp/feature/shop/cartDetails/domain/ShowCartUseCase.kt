package com.doubleclick.rovleapp.feature.shop.cartDetails.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.cart.response.getCart.NewCart
import javax.inject.Inject

class ShowCartUseCase @Inject constructor(private val showCartRepository: ShopRepository) :
    UseCase<NewCart, ShowCartUseCase.Params>() {

    override suspend fun run(params: Params) = showCartRepository.showCart(params.id)

    data class Params(val id: String)

}