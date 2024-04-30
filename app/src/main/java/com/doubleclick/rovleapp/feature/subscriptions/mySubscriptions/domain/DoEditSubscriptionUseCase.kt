package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsRepository
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import javax.inject.Inject

class DoEditSubscriptionUseCase @Inject constructor(private val editSubscriptionRepository: SubscriptionsRepository) :
    UseCase<SubscriptionData, DoEditSubscriptionUseCase.Params>() {

    override suspend fun run(params: Params) = editSubscriptionRepository.editSubscription(
        params.id,
        params.periodicity,
        params.planId,
        params.sizeId,
        params.notes,
        params.deliveryType,
        params.zipCode,
        params.name,
        params.phone,
        params.email,
        params.shipping,
        params.coffeeShopId,
        params.lockerLocation,
        params.address
    )

    data class Params(
        val id: String,
        val periodicity: String,
        val planId: String,
        val sizeId: Int,
        val notes: String,
        val deliveryType: String,
        val zipCode: String,
        val name: String,
        val phone: String,
        val email: String,
        val shipping: String,
        val coffeeShopId: String,
        val lockerLocation: String,
        val address: String
    )

}