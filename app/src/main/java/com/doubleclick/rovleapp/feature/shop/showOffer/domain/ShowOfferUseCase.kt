package com.doubleclick.rovleapp.feature.shop.showOffer.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.showOffer.data.ShowOfferData
import javax.inject.Inject

class ShowOfferUseCase @Inject constructor(private val showOfferRepository: ShopRepository) :
    UseCase<ShowOfferData, ShowOfferUseCase.Params>() {

    override suspend fun run(params: Params) = showOfferRepository.showOffer(params.id)

    data class Params(val id: String)

}