package com.doubleclick.rovleapp.feature.subscriptions.filterPlan.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.listPlans.PlansData
import javax.inject.Inject

class FilterPlanUseCase @Inject constructor(private val repository: SubscriptionsRepository) :
    UseCase<List<PlansData>, FilterPlanUseCase.Params>() {

    override suspend fun run(params: Params) =
        repository.filterPlan(
            params.name,
            params.weightScoreFrom,
            params.weightScoreTo,
            params.providers,
            params.priceFrom,
            params.priceTo
        )

    data class Params(
       val  name: String?,
       val  weightScoreFrom: String?,
       val  weightScoreTo: String?,
       val  providers: List<String>,
       val  priceFrom: String?,
       val  priceTo: String?
    )

}