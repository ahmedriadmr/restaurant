package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeApi {
    companion object {
        private const val CATEGORIES = "categories"
        private const val LOGOUT = "logout"
        private const val CART = "carts"

    }

    @GET(CATEGORIES)
    suspend fun getCategories(): Response<DataWrapper<List<Categories>>>

    @DELETE(LOGOUT)
    suspend fun logout(): Response<DataWrapper<LogoutResponse>>

    @POST(CART)
    suspend fun putCart(@Body request:PutCartRequest) : Response<DataWrapper<PutCartResponse>>

    @GET(CART)
    suspend fun getCart(): Response<DataWrapper<List<CartData>>>

}
