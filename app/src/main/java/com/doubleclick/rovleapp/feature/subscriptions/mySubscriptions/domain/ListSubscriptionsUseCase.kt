package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.listSubscriptions.MySubscriptionsData
import javax.inject.Inject

class ListSubscriptionsUseCase @Inject constructor(private val mySubscriptionsRepository: SubscriptionsRepository) :
    UseCase<List<MySubscriptionsData>, UseCase.None>() {

    override suspend fun run(params: None) = mySubscriptionsRepository.listSubscriptions()

}