package com.doubleclick.rovleapp.feature.shop.cartDetails.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails.OrderDetailsData
import javax.inject.Inject

class OrderDetailsUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<OrderDetailsData, OrderDetailsUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getOrderDetails(params.providerId)

    data class Params(val providerId: String)
}