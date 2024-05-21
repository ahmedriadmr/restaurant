package com.doubleclick.restaurant.feature.admin.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.admin.AdminRepository
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.AddProductRequest
import com.doubleclick.restaurant.feature.admin.data.addProduct.response.AddProductResponse
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val repository: AdminRepository) :
    UseCase<AddProductResponse, AddProductUseCase.Params>() {

    override suspend fun run(params: Params) = repository.addProduct(params.request)

    data class Params(val request: AddProductRequest)
}