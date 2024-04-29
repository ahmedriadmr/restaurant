package com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan

data class ShowPlanResponse(
    val `data`: PlanDetails,
    val message: String,
    val status: String
)