package com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.domain
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.request.PaySubscriptionRequest
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.response.PaySubscriptionData
import javax.inject.Inject

class PaySubscriptionUseCase @Inject constructor(private val paySubscriptionRepository: SubscriptionsRepository) : UseCase<PaySubscriptionData, PaySubscriptionUseCase.Params>() {

    override suspend fun run(params: Params) = paySubscriptionRepository.paySubscription(
        params.request
    )

    data class Params(
        val request: PaySubscriptionRequest
    )
}