package com.doubleclick.restaurant.feature.shop.payment.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.payment.data.request.PayOrderRequest
import com.doubleclick.restaurant.feature.shop.payment.data.response.PayOrderData
import javax.inject.Inject

class PayOrderUseCase @Inject constructor(private val payOrderRepository: ShopRepository) :
    UseCase<PayOrderData, PayOrderUseCase.Params>() {

    override suspend fun run(params: Params) = payOrderRepository.payOrder(
        params.request
    )

    data class Params(
        val request: PayOrderRequest
    )
}