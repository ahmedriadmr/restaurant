package com.doubleclick.rovleapp.feature.shop.cart.usecase

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.cart.request.putCart.PutCartRequest
import com.doubleclick.rovleapp.feature.shop.cart.response.putCart.StoreData
import javax.inject.Inject

class PutCartWithOrWithoutOfferUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<StoreData, PutCartWithOrWithoutOfferUseCase.Params>() {

    override suspend fun run(params: Params) = repository.putCartWithOrWithoutOffer(params.request,params.offerId )

    data class Params(val request: PutCartRequest , val offerId:String?)
}