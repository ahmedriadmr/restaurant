package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.orders.showOrder.ShowOrderData
import javax.inject.Inject

class ShowOrderUseCase @Inject constructor(private val showOrderRepository: ProfileRepository) :
    UseCase<ShowOrderData, ShowOrderUseCase.Params>() {

    override suspend fun run(params: Params) = showOrderRepository.showOrder(params.id)

    data class Params(val id: String)

}