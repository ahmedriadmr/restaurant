package com.doubleclick.restaurant.feature.profile

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.NetworkHandler
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.profile.data.addAddress.request.AddAddressRequest
import com.doubleclick.restaurant.feature.profile.data.addAddress.response.AddressData
import com.doubleclick.restaurant.feature.profile.data.changePassword.ChangePassword
import com.doubleclick.restaurant.feature.profile.data.listAddresses.AddressesData
import com.doubleclick.restaurant.feature.profile.data.orders.listOrders.ListOrdersData
import com.doubleclick.restaurant.feature.profile.data.orders.showOrder.ShowOrderData
import com.doubleclick.restaurant.feature.profile.data.points.PointsData
import com.doubleclick.restaurant.feature.profile.data.rates.RatingData
import com.doubleclick.restaurant.feature.profile.data.rates.RatingOrderRequest
import com.doubleclick.restaurant.feature.profile.data.showProfile.ProfileDetails
import com.doubleclick.restaurant.feature.profile.data.updateAddress.UpdateAddressData
import com.doubleclick.restaurant.feature.profile.data.visits.VisitsData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface ProfileRepository {

    suspend fun showProfile(): Either<Failure, ProfileDetails>
    suspend fun updateProfile(
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
    ): Either<Failure, ProfileDetails>
    suspend fun changePassword(request: ChangePassword): Either<Failure, ProfileDetails>

    suspend fun showPoints(): Either<Failure, List<PointsData>>
    suspend fun showVisits(): Either<Failure, List<VisitsData>>
    suspend fun listOrders(page: Int): Either<Failure, ListOrdersData>

    suspend fun showOrder(id: String): Either<Failure, ShowOrderData>

    suspend fun logout(): Either<Failure, String>
    suspend fun ratingOrder(request: RatingOrderRequest): Either<Failure, RatingData>

    suspend fun addAddress(request: AddAddressRequest): Either<Failure, AddressData>
    suspend fun showAddress(id: String): Either<Failure, AddressData>
    suspend fun updateAddress(
        addressId: String,countryId: String,provinceId: String,cityId: String,address: String,zip: String,userName: String,email: String,phone: String
    ): Either<Failure, UpdateAddressData>

    suspend fun getAddresses(): Either<Failure, List<AddressesData>>
    suspend fun deleteAddress(addressId: String): Either<Failure, String>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: ProfileService,
        private val appSettingsSource: AppSettingsSource
    ) : ProfileRepository {
        override suspend fun showProfile(): Either<Failure, ProfileDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showProfile()) {
                    localSource(it.data)
                    it.data
                }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


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
        ): Either<Failure, ProfileDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.updateProfile(
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
                ) {
                    localSource(it.data)
                    it.data
                }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun changePassword(request: ChangePassword): Either<Failure, ProfileDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.changePassword(request)) { localSource(it.data)
                    it.data
                }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun showPoints(): Either<Failure, List<PointsData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showPoints()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun showVisits(): Either<Failure, List<VisitsData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showVisits()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun listOrders(page: Int): Either<Failure, ListOrdersData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.listOrders(page)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun showOrder(id: String): Either<Failure, ShowOrderData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showOrder(id)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun logout(): Either<Failure, String> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.logout()) {
                    clearLocal()
                    it.data
                }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun ratingOrder(request: RatingOrderRequest): Either<Failure, RatingData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.ratingOrder(request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun addAddress(request: AddAddressRequest): Either<Failure, AddressData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.addAddress(request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun showAddress(id: String): Either<Failure, AddressData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showAddress(id)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun updateAddress(
            addressId: String,countryId: String,provinceId: String,cityId: String,address: String,zip: String,userName: String,email: String,phone: String
        ): Either<Failure, UpdateAddressData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.updateAddress(addressId, countryId, provinceId, cityId,address,zip,userName,email,phone)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun deleteAddress(addressId: String): Either<Failure, String> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.deleteAddress(addressId)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getAddresses(): Either<Failure, List<AddressesData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getAddresses()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }



        private fun clearLocal(){
            runBlocking {
                appSettingsSource.saveUserAccess(null)
            }
        }

        private fun localSource(model: ProfileDetails) {
            runBlocking {
                model.let { data ->
                    appSettingsSource.saveUserAccess(
                        appSettingsSource.user().firstOrNull()?.copy(
                            address = model.address,
                            cardId = model.card_id,
                            cityId = model.city_id.toString(),
                            countryId = model.country_id.toString(),
                            email = model.email,
                            id = model.id,
                            image = model.image.toString(),
                            levelId = model.level_id,
                            name = model.name,
                            phone = data.phone,
                            provinceId = data.province_id.toString(),
                            zip = data.zip
                        )
                    )
                }
            }
        }

        private fun <T, R> request(response: Response<T>, transform: (T) -> R): Either<Failure, R> {
            return try {
                when (response.isSuccessful && response.body() != null) {
                    true -> Either.Success(transform((response.body()!!)))
                    false -> when (response.code()) {
                        404 -> Either.Failure(Failure.ServerError)
                        401 -> Either.Failure(Failure.Authentication)
                        else -> Either.Failure(Failure.FeatureFailure(response.errorBody()?.string()?.let { JSONObject(it).getString("message") }))
                    }

                }
            } catch (exception: Throwable) {
                Either.Failure(Failure.UnExpectedError(exception.message))
            }
        }
    }
}
