package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import javax.inject.Inject

class ShowSubscriptionUseCase @Inject constructor(private val showSubscriptionRepository: SubscriptionsRepository) :
    UseCase<SubscriptionData, ShowSubscriptionUseCase.Params>() {

    override suspend fun run(params: Params) =
        showSubscriptionRepository.showSubscription(params.id)

    data class Params(val id: String)

}