package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.listSubscriptions.MySubscriptionsData
import javax.inject.Inject

class ListSubscriptionsUseCase @Inject constructor(private val mySubscriptionsRepository: SubscriptionsRepository) :
    UseCase<List<MySubscriptionsData>, UseCase.None>() {

    override suspend fun run(params: None) = mySubscriptionsRepository.listSubscriptions()

}