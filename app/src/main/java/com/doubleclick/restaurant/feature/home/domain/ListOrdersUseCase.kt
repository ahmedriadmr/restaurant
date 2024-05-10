package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.SearchOrdersData
import javax.inject.Inject

class ListOrdersUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<List<SearchOrdersData>, UseCase.None>() {

    override suspend fun run(params: None) = repository.listOrders()


}