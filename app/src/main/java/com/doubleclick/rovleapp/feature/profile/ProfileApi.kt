package com.doubleclick.rovleapp.feature.profile

import com.doubleclick.rovleapp.core.functional.DataWrapper
import com.doubleclick.rovleapp.feature.profile.data.addAddress.request.AddAddressRequest
import com.doubleclick.rovleapp.feature.profile.data.addAddress.response.AddressData
import com.doubleclick.rovleapp.feature.profile.data.changePassword.ChangePassword
import com.doubleclick.rovleapp.feature.profile.data.listAddresses.AddressesData
import com.doubleclick.rovleapp.feature.profile.data.orders.listOrders.ListOrdersData
import com.doubleclick.rovleapp.feature.profile.data.orders.showOrder.ShowOrderData
import com.doubleclick.rovleapp.feature.profile.data.points.PointsData
import com.doubleclick.rovleapp.feature.profile.data.rates.RatingData
import com.doubleclick.rovleapp.feature.profile.data.rates.RatingOrderRequest
import com.doubleclick.rovleapp.feature.profile.data.showProfile.ProfileDetails
import com.doubleclick.rovleapp.feature.profile.data.updateAddress.UpdateAddressData
import com.doubleclick.rovleapp.feature.profile.data.visits.VisitsData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {

    companion object {
        private const val Profile = "profile"
        private const val Points = "points"
        private const val orders = "https://rovle.eslamghazy.net/api/v2/orders"
        private const val logout = "auth/logout"
        private const val rating = "rate"
        private const val addresses = "addresses"
        private const val address = "addresses/{id}"
        private const val visits = "visits"
    }

    @GET(Profile)
    suspend fun showProfile(): Response<DataWrapper<ProfileDetails>>

    @POST(Profile)
    @Multipart
    suspend fun updateProfile(
        @Part("name") name: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("zip") zip: RequestBody?,
        @Part("card_id") cardId: RequestBody?,
        @Part("country_id") countryId: RequestBody?,
        @Part("province_id") provinceId: RequestBody?,
        @Part("city_id") cityId: RequestBody?,
        @Part image: MultipartBody.Part?,
        @Part("email") email: RequestBody?
    ): Response<DataWrapper<ProfileDetails>>

    @POST(Profile)
    suspend fun changePassword(@Body request: ChangePassword): Response<DataWrapper<ProfileDetails>>

    @GET(Points)
    suspend fun showPoints(): Response<DataWrapper<List<PointsData>>>

    @GET(visits)
    suspend fun showVisits(): Response<DataWrapper<List<VisitsData>>>


    @GET(orders)
    suspend fun listOrders(@Query("page") page: Int): Response<DataWrapper<ListOrdersData>>

    @GET("${orders}/{id}")
    suspend fun showOrder(@Path("id") id: String): Response<DataWrapper<ShowOrderData>>

    @POST(logout)
    suspend fun logout(): Response<DataWrapper<String>>

    @POST(rating)
    suspend fun ratingOrder(@Body request: RatingOrderRequest): Response<DataWrapper<RatingData>>

    @POST(addresses)
    suspend fun addAddress(@Body request: AddAddressRequest): Response<DataWrapper<AddressData>>

    @GET(address)
    suspend fun showAddress(@Path("id") id: String): Response<DataWrapper<AddressData>>

    @PUT(address)
    @FormUrlEncoded
    suspend fun updateAddress(
        @Path("id") addressId: String,
        @Field("country_id") countryId: String,
        @Field("province_id") provinceId: String,
        @Field("city_id") cityId: String,
        @Field("address") address: String,
        @Field("zip") zip: String,
        @Field("user_name") userName: String,
        @Field("email") email: String,
        @Field("phone") phone: String
    ): Response<DataWrapper<UpdateAddressData>>

    @DELETE(address)
    suspend fun deleteAddress(@Path("id") addressId: String): Response<DataWrapper<String>>

    @GET(addresses)
    suspend fun getAddresses(): Response<DataWrapper<List<AddressesData>>>

}