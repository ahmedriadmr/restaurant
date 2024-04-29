package com.doubleclick.rovleapp.feature.shop.payment.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.payment.data.request.PayOrderRequest
import com.doubleclick.rovleapp.feature.shop.payment.data.response.PayOrderData
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