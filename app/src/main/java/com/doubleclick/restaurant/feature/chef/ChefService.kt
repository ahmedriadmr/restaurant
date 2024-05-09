package com.doubleclick.restaurant.feature.chef

import com.doubleclick.restaurant.feature.chef.domain.ChefApi
import com.doubleclick.restaurant.feature.chef.domain.model.Message
import com.doubleclick.restaurant.feature.chef.domain.model.OrderChef
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import com.doubleclick.restaurant.feature.home.HomeApi
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ChefService @Inject constructor(retrofit: Retrofit) : ChefApi {

    private val chefApi by lazy { retrofit.create(ChefApi::class.java) }
    override suspend fun getOrdersChef(state: Status): Response<OrderChef> =
        chefApi.getOrdersChef(state)

    override suspend fun finished(order_id: String): Response<Message> = chefApi.finished(order_id)


}