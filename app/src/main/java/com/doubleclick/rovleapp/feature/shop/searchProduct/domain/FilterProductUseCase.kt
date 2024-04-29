package com.doubleclick.rovleapp.feature.shop.searchProduct.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.response.ProductData
import javax.inject.Inject

class FilterProductUseCase
@Inject constructor(private val repository: ShopRepository) :
    UseCase<ProductData, FilterProductUseCase.Params>() {

    override suspend fun run(params: Params) =
        repository.filterProduct(
            params.query,
            params.origins,
            params.scaScoreFrom,
            params.scaScoreTo,
            params.providers,
            params.altitudeFrom,
            params.altitudeTo,
            params.page
        )

    data class Params(
        val query: String,
        val origins: List<Int>,
        val scaScoreFrom: String,
        val scaScoreTo: String,
        val providers: List<String>,
        val altitudeFrom: String,
        val altitudeTo: String,
        val page: Int
    )

}