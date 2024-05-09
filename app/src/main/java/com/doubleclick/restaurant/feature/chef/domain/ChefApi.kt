package com.doubleclick.restaurant.feature.chef.domain

import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.feature.chef.domain.model.Message
import com.doubleclick.restaurant.feature.chef.domain.model.OrderChef
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ChefApi {


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("orders/search")
    suspend fun getOrdersChef(@Body state: Status): Response<OrderChef>

    @POST("orders/{id}/chief_update")
    suspend fun finished(@Path("id") order_id: String): Response<Message>

}