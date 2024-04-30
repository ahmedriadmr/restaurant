package com.doubleclick.restaurant.feature.shop.searchProduct.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.searchProduct.data.providers.ProvidersData
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<ProvidersData, GetProvidersUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getProviders(params.page)

    data class Params(val page: Int)

}