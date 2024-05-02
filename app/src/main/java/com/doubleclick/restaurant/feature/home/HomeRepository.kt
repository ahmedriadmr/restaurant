package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.NetworkHandler
import com.doubleclick.restaurant.feature.home.data.Categories
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface HomeRepository {

    suspend fun getCategories(): Either<Failure, List<Categories>>
    suspend fun logout(): Either<Failure, String>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: HomeService
    ) : HomeRepository {
        override suspend fun getCategories(): Either<Failure, List<Categories>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getCategories()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun logout(): Either<Failure, String> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.logout()) { it.data }
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
                        else -> Either.Failure(Failure.FeatureFailure(response.errorBody()?.string()?.let { JSONObject(it).getString("message") }))
                    }

                }
            } catch (exception: Throwable) {
                Either.Failure(Failure.UnExpectedError(exception.message))
            }
        }
    }
}