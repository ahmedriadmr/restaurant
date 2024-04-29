package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.rates.RatingData
import com.doubleclick.rovleapp.feature.profile.data.rates.RatingOrderRequest
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