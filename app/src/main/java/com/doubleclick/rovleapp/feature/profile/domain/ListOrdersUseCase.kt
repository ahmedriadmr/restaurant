package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.orders.listOrders.ListOrdersData
import javax.inject.Inject

class ListOrdersUseCase @Inject constructor(private val listOrdersRepository: ProfileRepository) :
    UseCase<ListOrdersData, ListOrdersUseCase.Params>() {

    override suspend fun run(params: Params) = listOrdersRepository.listOrders(params.page)

    data class Params(val page: Int)

}