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
import retrofit2.Retrofit
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileService @Inject constructor(retrofit: Retrofit) : ProfileApi {

    private val profileApi by lazy { retrofit.create(ProfileApi::class.java) }
    override suspend fun showProfile(): Response<DataWrapper<ProfileDetails>> =
        profileApi.showProfile()

    override suspend fun updateProfile(
        name: RequestBody?,
        phone: RequestBody?,
        password: RequestBody?,
        address: RequestBody?,
        zip: RequestBody?,
        cardId: RequestBody?,
        countryId: RequestBody?,
        provinceId: RequestBody?,
        cityId: RequestBody?,
        image: MultipartBody.Part?,
        email: RequestBody?
    ): Response<DataWrapper<ProfileDetails>> = profileApi.updateProfile(
        name,
        phone,
        password,
        address,
        zip,
        cardId,
        countryId,
        provinceId,
        cityId,
        image,
        email
    )

    override suspend fun changePassword(request: ChangePassword): Response<DataWrapper<ProfileDetails>> =
        profileApi.changePassword(request)
    override suspend fun showPoints(): Response<DataWrapper<List<PointsData>>> =
        profileApi.showPoints()
    override suspend fun showVisits(): Response<DataWrapper<List<VisitsData>>> =
        profileApi.showVisits()
    override suspend fun listOrders(page: Int): Response<DataWrapper<ListOrdersData>> =
        profileApi.listOrders(page)

    override suspend fun showOrder(id: String): Response<DataWrapper<ShowOrderData>> =
        profileApi.showOrder(id)

    override suspend fun logout(): Response<DataWrapper<String>> =
        profileApi.logout()

    override suspend fun ratingOrder(@Body request: RatingOrderRequest): Response<DataWrapper<RatingData>> =
        profileApi.ratingOrder(request)

    override suspend fun addAddress(@Body request: AddAddressRequest): Response<DataWrapper<AddressData>> =
        profileApi.addAddress(request)
    override suspend fun showAddress(id: String): Response<DataWrapper<AddressData>> =
        profileApi.showAddress(id)
    override suspend fun updateAddress(
      addressId: String,countryId: String,provinceId: String,cityId: String,address: String,zip: String,userName: String,email: String,phone: String
    ): Response<DataWrapper<UpdateAddressData>> = profileApi.updateAddress(addressId, countryId, provinceId, cityId,address,zip,userName,email,phone)

    override suspend fun deleteAddress(addressId: String): Response<DataWrapper<String>> = profileApi.deleteAddress(addressId)

    override suspend fun getAddresses(): Response<DataWrapper<List<AddressesData>>> = profileApi.getAddresses()

}

