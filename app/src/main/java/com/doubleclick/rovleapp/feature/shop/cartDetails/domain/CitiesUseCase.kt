package com.doubleclick.restaurant.feature.shop.cartDetails.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import javax.inject.Inject

class CitiesUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<List<FilterCitiesData>, CitiesUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getCities(params.zip)
    data class Params(val zip: String)

}