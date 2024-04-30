package com.doubleclick.restaurant.feature.shop.cart.usecase

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.cart.request.putCart.PutCartRequest
import com.doubleclick.restaurant.feature.shop.cart.response.putCart.StoreData
import javax.inject.Inject

class PutCartWithOrWithoutOfferUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<StoreData, PutCartWithOrWithoutOfferUseCase.Params>() {

    override suspend fun run(params: Params) = repository.putCartWithOrWithoutOffer(params.request,params.offerId )

    data class Params(val request: PutCartRequest , val offerId:String?)
}