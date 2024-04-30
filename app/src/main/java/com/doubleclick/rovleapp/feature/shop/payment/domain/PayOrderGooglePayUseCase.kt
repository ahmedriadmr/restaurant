package com.doubleclick.restaurant.feature.shop.payment.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.payment.data.googlePay.request.PayOrderGooglePayRequest
import com.doubleclick.restaurant.feature.shop.payment.data.googlePay.response.PayOrderGooglePayData
import javax.inject.Inject

class PayOrderGooglePayUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<PayOrderGooglePayData, PayOrderGooglePayUseCase.Params>() {

    override suspend fun run(params: Params) = repository.payOrderGooglePay(
        params.request
    )

    data class Params(
        val request: PayOrderGooglePayRequest
    )
}