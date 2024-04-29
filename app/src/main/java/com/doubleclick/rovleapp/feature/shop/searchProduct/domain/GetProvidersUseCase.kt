package com.doubleclick.rovleapp.feature.shop.searchProduct.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.providers.ProvidersData
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<ProvidersData, GetProvidersUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getProviders(params.page)

    data class Params(val page: Int)

}