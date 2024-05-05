package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.data.UpdateProfileResponse
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.userProfile.UserProfileData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HomeApi {
    companion object {
        private const val CATEGORIES = "categories"
        private const val LOGOUT = "logout"
        private const val CART = "carts"
        private const val PROFILE = "user_profile"
        private const val UPDATEPROFILE = "user_profile/update"

    }

    @GET(CATEGORIES)
    suspend fun getCategories(): Response<DataWrapper<List<Categories>>>

    @DELETE(LOGOUT)
    suspend fun logout(): Response<LogoutResponse>

    @POST(CART)
    suspend fun putCart(@Body request:PutCartRequest) : Response<DataWrapper<PutCartResponse>>

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

}
