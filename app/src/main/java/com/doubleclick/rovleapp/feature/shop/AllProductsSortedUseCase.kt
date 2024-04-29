package com.doubleclick.rovleapp.feature.shop

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.response.ProductData
import javax.inject.Inject

class AllProductsSortedUseCase @Inject constructor(private val allProductsSortedRepository: ShopRepository) :
    UseCase<ProductData, AllProductsSortedUseCase.Params>() {

    override suspend fun run(params: Params) = allProductsSortedRepository.allProductsSorted(params.page,params.orderBy,params.orderType)
    data class Params(val page: Int, val orderBy:String , val orderType:String)

}