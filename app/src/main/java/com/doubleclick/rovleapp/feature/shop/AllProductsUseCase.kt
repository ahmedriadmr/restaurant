package com.doubleclick.rovleapp.feature.shop

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.response.ProductData
import javax.inject.Inject

class AllProductsUseCase
@Inject constructor(private val allProductsRepository: ShopRepository) :
    UseCase<ProductData, AllProductsUseCase.Params>() {

    override suspend fun run(params: Params) = allProductsRepository.allProducts(params.page)
    data class Params(val page: Int)

}