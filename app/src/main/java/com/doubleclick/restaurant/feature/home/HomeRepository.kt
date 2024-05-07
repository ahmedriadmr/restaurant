package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.NetworkHandler
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.data.UpdateProfileResponse
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.makeOrder.request.MakeOrderRequest
import com.doubleclick.restaurant.feature.home.data.makeOrder.response.MakeOrderResponse
import com.doubleclick.restaurant.feature.home.data.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.home.data.updateCart.response.UpdateCartResponse
import com.doubleclick.restaurant.feature.home.data.userProfile.UserProfileData
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface HomeRepository {

    suspend fun getCategories(): Either<Failure, List<Categories>>
    suspend fun logout(): Either<Failure, LogoutResponse>

    suspend fun putCart(request: PutCartRequest): Either<Failure, PutCartResponse>
    suspend fun updateCart(id: String, request: UpdateCartRequest): Either<Failure, UpdateCartResponse>

    suspend fun getCart(): Either<Failure, List<CartData>>

    suspend fun userProfile(): Either<Failure, UserProfileData>
    suspend fun updateProfile(
        firstName: RequestBody?,
        lastName: RequestBody?,
        email: RequestBody?,
        password: RequestBody?,
        passwordConfirmation: RequestBody?,
        phone: RequestBody?,
        address: RequestBody?
    ): Either<Failure, UpdateProfileResponse>

    suspend fun makeOrder(request: MakeOrderRequest): Either<Failure, MakeOrderResponse>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: HomeService,
        private val appSettingsSource: AppSettingsSource
    ) : HomeRepository {
        override suspend fun getCategories(): Either<Failure, List<Categories>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getCategories()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun logout(): Either<Failure, LogoutResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.logout()) {
                    clearLocal()
                    it
                }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun putCart(request: PutCartRequest): Either<Failure, PutCartResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.putCart(request)) { it }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun updateCart(id: String, request: UpdateCartRequest): Either<Failure, UpdateCartResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.updateCart(id, request)) { it }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getCart(): Either<Failure, List<CartData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getCart()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun userProfile(): Either<Failure, UserProfileData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.userProfile()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun updateProfile(
            firstName: RequestBody?,
            lastName: RequestBody?,
            email: RequestBody?,
            password: RequestBody?,
            passwordConfirmation: RequestBody?,
            phone: RequestBody?,
            address: RequestBody?
        ): Either<Failure, UpdateProfileResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.updateProfile(
                        firstName,
                        lastName,
                        email,
                        password,
                        passwordConfirmation,
                        phone,
                        address
                    )
                ) { it }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun makeOrder(request: MakeOrderRequest): Either<Failure, MakeOrderResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.makeOrder(request)) { it }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        private fun clearLocal() {
            runBlocking {
                appSettingsSource.saveUserAccess(null)
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