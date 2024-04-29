package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import javax.inject.Inject

class DoInitSubscriptionStatus @Inject constructor(private val pauseSubscriptionRepository: SubscriptionsRepository) :
    UseCase<SubscriptionData, DoInitSubscriptionStatus.Params>() {

    override suspend fun run(params: Params) =
        pauseSubscriptionRepository.pauseSubscription(params.id, params.status, params.activationDate)

    data class Params(val id: String, val status: String, val activationDate: String)

}