package com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.domain
import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.request.PaySubscriptionRequest
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.response.PaySubscriptionData
import javax.inject.Inject

class PaySubscriptionUseCase @Inject constructor(private val paySubscriptionRepository: SubscriptionsRepository) : UseCase<PaySubscriptionData, PaySubscriptionUseCase.Params>() {

    override suspend fun run(params: Params) = paySubscriptionRepository.paySubscription(
        params.request
    )

    data class Params(
        val request: PaySubscriptionRequest
    )
}