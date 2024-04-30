package com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.googlePay.request.PaySubscriptionGooglePayRequest
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.googlePay.response.PaySubscriptionGooglePayData
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