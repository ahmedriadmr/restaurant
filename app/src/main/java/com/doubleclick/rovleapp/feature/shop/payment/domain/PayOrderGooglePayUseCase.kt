package com.doubleclick.rovleapp.feature.shop.payment.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.payment.data.googlePay.request.PayOrderGooglePayRequest
import com.doubleclick.rovleapp.feature.shop.payment.data.googlePay.response.PayOrderGooglePayData
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