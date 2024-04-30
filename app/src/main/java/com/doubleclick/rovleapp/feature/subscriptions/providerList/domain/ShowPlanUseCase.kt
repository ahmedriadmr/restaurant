package com.doubleclick.restaurant.feature.subscriptions.providerList.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan.PlanDetails
import javax.inject.Inject

class ShowPlanUseCase @Inject constructor(private val showPlanRepository: SubscriptionsRepository) :
    UseCase<PlanDetails, ShowPlanUseCase.Params>() {

    override suspend fun run(params: Params) = showPlanRepository.showPlan(params.id)

    data class Params(val id: String)

}