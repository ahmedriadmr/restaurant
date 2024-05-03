package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import javax.inject.Inject

class PutCartUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<PutCartResponse, PutCartUseCase.Params>() {

    override suspend fun run(params: Params) = repository.putCart(params.request)

    data class Params(val request: PutCartRequest)
}