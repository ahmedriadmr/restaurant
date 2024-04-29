package com.doubleclick.rovleapp.feature.passport.offers.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.passport.PassportRepository
import com.doubleclick.rovleapp.feature.passport.offers.data.Offers
import javax.inject.Inject

class ListOffersUseCase @Inject constructor(private val repository: PassportRepository) :
    UseCase<Offers, UseCase.None>() {

    override suspend fun run(params: None) = repository.listOffers()


}