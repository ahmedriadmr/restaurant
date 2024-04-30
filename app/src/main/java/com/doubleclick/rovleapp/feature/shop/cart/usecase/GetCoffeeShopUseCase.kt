package com.doubleclick.restaurant.feature.shop.cart.usecase

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.response.CoffeeShop
import javax.inject.Inject

class GetCoffeeShopUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<List<CoffeeShop>, GetCoffeeShopUseCase.Params>() {

    override suspend fun run(params: Params) =
        repository.getCoffeeShops(params.zip,params.providerId)

    data class Params(val zip: String, val providerId: String)

}



