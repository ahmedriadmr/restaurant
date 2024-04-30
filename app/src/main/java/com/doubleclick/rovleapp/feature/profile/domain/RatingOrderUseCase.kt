package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.rates.RatingData
import com.doubleclick.restaurant.feature.profile.data.rates.RatingOrderRequest
import javax.inject.Inject

class RatingOrderUseCase @Inject constructor(private val ratingOrderRepository: ProfileRepository) :
    UseCase<RatingData, RatingOrderUseCase.Params>() {

    override suspend fun run(params: Params) = ratingOrderRepository.ratingOrder(
        params.request
    )

    data class Params(
        val request: RatingOrderRequest
    )
}