package com.doubleclick.rovleapp.feature.shop.searchProduct.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.origins.OriginsData
import javax.inject.Inject

class GetOriginsUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<OriginsData, GetOriginsUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getOrigins(params.page)
    data class Params(val page: Int)
}