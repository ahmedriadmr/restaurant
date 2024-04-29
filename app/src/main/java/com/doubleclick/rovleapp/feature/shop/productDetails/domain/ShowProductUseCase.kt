package com.doubleclick.rovleapp.feature.shop.productDetails.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.response.Product
import javax.inject.Inject

class ShowProductUseCase
@Inject constructor(private val showProductRepository: ShopRepository) :
    UseCase<Product, ShowProductUseCase.Params>() {

    override suspend fun run(params: Params) = showProductRepository.showProduct(params.id)

    data class Params(val id: String)

}