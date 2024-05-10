package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.data.UpdateProfileResponse
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderRequest
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderResponse
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.makeOrder.request.MakeOrderRequest
import com.doubleclick.restaurant.feature.home.data.makeOrder.response.MakeOrderResponse
import com.doubleclick.restaurant.feature.home.data.searchOrders.request.SearchOrdersRequest
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.SearchOrdersData
import com.doubleclick.restaurant.feature.home.data.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.home.data.updateCart.response.UpdateCartResponse
import com.doubleclick.restaurant.feature.home.data.userProfile.UserProfileData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface HomeApi {
    companion object {
        private const val CATEGORIES = "categories"
        private const val LOGOUT = "logout"
        private const val CART = "carts"
        private const val PROFILE = "user_profile"
        private const val UPDATEPROFILE = "user_profile/update"
        private const val ORDER = "orders"

    }

    @GET(CATEGORIES)
    suspend fun getCategories(): Response<DataWrapper<List<Categories>>>

    @DELETE(LOGOUT)
    suspend fun logout(): Response<LogoutResponse>

    @POST(CART)
    suspend fun putCart(@Body request:PutCartRequest) : Response<PutCartResponse>
    @POST("${CART}/{id}")
    suspend fun updateCart(@Path("id") id: String,@Body request:UpdateCartRequest) : Response<UpdateCartResponse>

    @GET(CART)
    suspend fun getCart(): Response<DataWrapper<List<CartData>>>

    @GET(PROFILE)
    suspend fun userProfile():Response<DataWrapper<UserProfileData>>

    @POST(UPDATEPROFILE)
    @Multipart
    suspend fun updateProfile(
        @Part("first_name") firstName: RequestBody?,
        @Part("last_name") lastName: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("password_confirmation") passwordConfirmation: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("address") address: RequestBody?,

    ): Response<UpdateProfileResponse>

    @POST(ORDER)
    suspend fun makeOrder(@Body request:MakeOrderRequest) : Response<MakeOrderResponse>

    @GET(ORDER)
    suspend fun listOrders(): Response<DataWrapper<List<SearchOrdersData>>>

    @POST("${ORDER}/{id}")
    suspend fun cancelOrder(@Path("id") id: String,@Body request:CancelOrderRequest) : Response<CancelOrderResponse>

    @POST("${ORDER}/search")
    suspend fun searchOrders(@Body request: SearchOrdersRequest): Response<DataWrapper<List<SearchOrdersData>>>

}
