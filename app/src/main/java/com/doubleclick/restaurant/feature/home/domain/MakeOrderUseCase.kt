package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.makeOrder.request.MakeOrderRequest
import com.doubleclick.restaurant.feature.home.data.makeOrder.response.MakeOrderResponse
import javax.inject.Inject

class MakeOrderUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<MakeOrderResponse, MakeOrderUseCase.Params>() {

    override suspend fun run(params: Params) = repository.makeOrder(params.request)

    data class Params(val request: MakeOrderRequest)
}