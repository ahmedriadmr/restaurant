package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import javax.inject.Inject

class ShowSubscriptionUseCase @Inject constructor(private val showSubscriptionRepository: SubscriptionsRepository) :
    UseCase<SubscriptionData, ShowSubscriptionUseCase.Params>() {

    override suspend fun run(params: Params) =
        showSubscriptionRepository.showSubscription(params.id)

    data class Params(val id: String)

}