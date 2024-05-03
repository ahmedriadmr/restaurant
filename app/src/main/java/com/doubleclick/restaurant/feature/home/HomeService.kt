package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeService @Inject constructor(retrofit: Retrofit) : HomeApi {
    private val homeApi by lazy { retrofit.create(HomeApi::class.java) }

    override suspend fun getCategories() = homeApi.getCategories()
    override suspend fun logout() = homeApi.logout()

    override suspend fun putCart(request: PutCartRequest) = homeApi.putCart(request)

    override suspend fun getCart() = homeApi.getCart()

}
