package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<PutCartResponse, DeleteCartUseCase.Params>() {

    override suspend fun run(params: Params) = repository.deleteCart(params.id)

    data class Params(val id:String)
}