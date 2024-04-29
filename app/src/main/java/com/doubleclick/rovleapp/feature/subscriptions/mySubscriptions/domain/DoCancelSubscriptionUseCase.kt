package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import javax.inject.Inject

class DoCancelSubscriptionUseCase @Inject constructor(private val cancelSubscriptionRepository: SubscriptionsRepository) :
    UseCase<String, DoCancelSubscriptionUseCase.Params>() {

    override suspend fun run(params: Params) =
        cancelSubscriptionRepository.cancelSubscription(params.id)

    data class Params(val id: String)

}