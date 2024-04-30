package com.doubleclick.restaurant.feature.shop.searchProduct.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.searchProduct.data.origins.OriginsData
import javax.inject.Inject

class GetOriginsUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<OriginsData, GetOriginsUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getOrigins(params.page)
    data class Params(val page: Int)
}