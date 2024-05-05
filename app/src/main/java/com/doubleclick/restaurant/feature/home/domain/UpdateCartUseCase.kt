package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.home.data.updateCart.response.UpdateCartResponse
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<UpdateCartResponse, UpdateCartUseCase.Params>() {

    override suspend fun run(params: Params) = repository.updateCart(params.id,params.request)

    data class Params(val id:String , val request: UpdateCartRequest)
}