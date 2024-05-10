package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderRequest
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderResponse
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<CancelOrderResponse, CancelOrderUseCase.Params>() {

    override suspend fun run(params: Params) = repository.cancelOrder(params.id,params.request)

    data class Params(val id:String , val request: CancelOrderRequest)
}