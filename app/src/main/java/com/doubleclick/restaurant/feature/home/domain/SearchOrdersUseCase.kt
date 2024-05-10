package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.searchOrders.request.SearchOrdersRequest
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.SearchOrdersData
import javax.inject.Inject

class SearchOrdersUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<List<SearchOrdersData>, SearchOrdersUseCase.Params>() {

    override suspend fun run(params: Params) = repository.searchOrders(params.request)

    data class Params(val request: SearchOrdersRequest)
}