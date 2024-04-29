package com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.request.PaySubscriptionGooglePayRequest
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.response.PaySubscriptionGooglePayData
import javax.inject.Inject

class PaySubscriptionGooglePayUseCase @Inject constructor(private val repository: SubscriptionsRepository) :
    UseCase<PaySubscriptionGooglePayData, PaySubscriptionGooglePayUseCase.Params>() {

    override suspend fun run(params: Params) = repository.paySubscriptionGooglePay(
        params.request
    )

    data class Params(
        val request: PaySubscriptionGooglePayRequest
    )
}