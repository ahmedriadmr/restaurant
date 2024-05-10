package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderRequest
import com.doubleclick.restaurant.feature.home.data.makeOrder.request.MakeOrderRequest
import com.doubleclick.restaurant.feature.home.data.searchOrders.request.SearchOrdersRequest
import com.doubleclick.restaurant.feature.home.data.updateCart.request.UpdateCartRequest
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeService @Inject constructor(retrofit: Retrofit) : HomeApi {
    private val homeApi by lazy { retrofit.create(HomeApi::class.java) }

    override suspend fun getCategories() = homeApi.getCategories()
    override suspend fun logout() = homeApi.logout()

    override suspend fun putCart(request: PutCartRequest) = homeApi.putCart(request)
    override suspend fun updateCart(id: String, request: UpdateCartRequest) = homeApi.updateCart(id,request)

    override suspend fun getCart() = homeApi.getCart()
    override suspend fun userProfile() = homeApi.userProfile()
    override suspend fun updateProfile(
        firstName: RequestBody?,
        lastName: RequestBody?,
        email: RequestBody?,
        password: RequestBody?,
        passwordConfirmation: RequestBody?,
        phone: RequestBody?,
        address: RequestBody?
    ) = homeApi.updateProfile(
        firstName,
        lastName,
        email,
        password,
        passwordConfirmation,
        phone,
        address
    )

    override suspend fun makeOrder(request: MakeOrderRequest) = homeApi.makeOrder(request)
    override suspend fun listOrders() = homeApi.listOrders()
    override suspend fun cancelOrder(id: String, request: CancelOrderRequest) = homeApi.cancelOrder(id,request)
    override suspend fun searchOrders(request: SearchOrdersRequest) = homeApi.searchOrders(request)
    override suspend fun deleteCart(id: String) = homeApi.deleteCart(id)


}
