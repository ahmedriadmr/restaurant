package com.doubleclick.restaurant.feature.subscriptions.providerList.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.subscribe.request.SubscribeRequest

import javax.inject.Inject

class DoSubscribeUseCase @Inject constructor(private val doSubscribeRepository: SubscriptionsRepository) :
    UseCase<SubscriptionData, DoSubscribeUseCase.Params>() {

    override suspend fun run(params: Params) = doSubscribeRepository.doSubscribe(params.request)

    data class Params(val request: SubscribeRequest)

}