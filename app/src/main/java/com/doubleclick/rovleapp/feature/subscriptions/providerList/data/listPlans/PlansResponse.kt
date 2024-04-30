package com.doubleclick.restaurant.feature.subscriptions.providerList.data.listPlans

data class PlansResponse(
    val `data`: List<PlansData>,
    val message: String,
    val status: String
)