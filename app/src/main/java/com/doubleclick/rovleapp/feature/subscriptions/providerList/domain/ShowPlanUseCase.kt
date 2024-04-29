package com.doubleclick.rovleapp.feature.subscriptions.providerList.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan.PlanDetails
import javax.inject.Inject

class ShowPlanUseCase @Inject constructor(private val showPlanRepository: SubscriptionsRepository) :
    UseCase<PlanDetails, ShowPlanUseCase.Params>() {

    override suspend fun run(params: Params) = showPlanRepository.showPlan(params.id)

    data class Params(val id: String)

}