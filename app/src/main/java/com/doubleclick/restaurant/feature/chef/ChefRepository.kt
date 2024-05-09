package com.doubleclick.restaurant.feature.chef

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.NetworkHandler
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.chef.domain.model.Data
import com.doubleclick.restaurant.feature.chef.domain.model.Message
import com.doubleclick.restaurant.feature.chef.domain.model.OrderChef
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import com.doubleclick.restaurant.feature.home.HomeService
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

interface ChefRepository {

    suspend fun getOrdersChef(state: Status): Either<Failure, List<Data>>
    suspend fun finished(order_id: String): Either<Failure, Message>

    class Network @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: ChefService,
        private val appSettingsSource: AppSettingsSource
    ) : ChefRepository {
        override suspend fun getOrdersChef(state: Status): Either<Failure, List<Data>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getOrdersChef(state)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun finished(order_id: String): Either<Failure, Message> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.finished(order_id)) { it }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        private fun <T, R> request(response: Response<T>, transform: (T) -> R): Either<Failure, R> {
            return try {
                when (response.isSuccessful && response.body() != null) {
                    true -> Either.Success(transform((response.body()!!)))
                    false -> when (response.code()) {
                        404 -> Either.Failure(Failure.ServerError)
                        401 -> Either.Failure(Failure.Authentication)
                        else -> Either.Failure(
                            Failure.FeatureFailure(
                                response.errorBody()?.string()
                                    ?.let { JSONObject(it).getString("message") })
                        )
                    }

                }
            } catch (exception: Throwable) {
                Either.Failure(Failure.UnExpectedError(exception.message))
            }
        }
    }
}