package com.doubleclick.restaurant.feature.shop.productDetails.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.response.Product
import javax.inject.Inject

class ShowProductUseCase
@Inject constructor(private val showProductRepository: ShopRepository) :
    UseCase<Product, ShowProductUseCase.Params>() {

    override suspend fun run(params: Params) = showProductRepository.showProduct(params.id)

    data class Params(val id: String)

}