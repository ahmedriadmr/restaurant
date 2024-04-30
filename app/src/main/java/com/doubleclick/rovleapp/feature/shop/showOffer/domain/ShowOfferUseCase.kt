package com.doubleclick.restaurant.feature.shop.showOffer.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.showOffer.data.ShowOfferData
import javax.inject.Inject

class ShowOfferUseCase @Inject constructor(private val showOfferRepository: ShopRepository) :
    UseCase<ShowOfferData, ShowOfferUseCase.Params>() {

    override suspend fun run(params: Params) = showOfferRepository.showOffer(params.id)

    data class Params(val id: String)

}