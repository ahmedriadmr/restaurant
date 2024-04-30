package com.doubleclick.restaurant.feature.shop

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.response.ProductData
import javax.inject.Inject

class AllProductsSortedUseCase @Inject constructor(private val allProductsSortedRepository: ShopRepository) :
    UseCase<ProductData, AllProductsSortedUseCase.Params>() {

    override suspend fun run(params: Params) = allProductsSortedRepository.allProductsSorted(params.page,params.orderBy,params.orderType)
    data class Params(val page: Int, val orderBy:String , val orderType:String)

}