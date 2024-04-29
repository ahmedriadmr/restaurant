package com.doubleclick.rovleapp.feature.shop.cartDetails.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import javax.inject.Inject

class CitiesUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<List<FilterCitiesData>, CitiesUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getCities(params.zip)
    data class Params(val zip: String)

}