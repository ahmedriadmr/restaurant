package com.doubleclick.rovleapp.feature.subscriptions.providerList.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.listPlans.PlansData
import javax.inject.Inject

class ListPlansUseCase @Inject constructor(private val subscriptionsRepository: SubscriptionsRepository) :
    UseCase<List<PlansData>, ListPlansUseCase.Params>() {

    override suspend fun run(params: Params) = subscriptionsRepository.listPlans(params.providerId)
    data class Params(
        val providerId: String
    )

}