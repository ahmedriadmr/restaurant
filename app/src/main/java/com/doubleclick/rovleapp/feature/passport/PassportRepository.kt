package com.doubleclick.rovleapp.feature.passport

import com.doubleclick.rovleapp.core.exception.Failure
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.platform.NetworkHandler
import com.doubleclick.rovleapp.feature.passport.logros.data.finishTask.FinishTaskRequest
import com.doubleclick.rovleapp.feature.passport.logros.data.newResponse.NewLogrosData
import com.doubleclick.rovleapp.feature.passport.offers.data.Offers
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface PassportRepository {

    suspend fun listOffers(): Either<Failure, Offers>
    suspend fun listLogros(): Either<Failure, NewLogrosData>
    suspend fun finishTask(request: FinishTaskRequest): Either<Failure, String>
    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: PassportService
    ) : PassportRepository {
        override suspend fun listOffers(): Either<Failure, Offers> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.listOffers()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun listLogros(): Either<Failure, NewLogrosData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.listLogros()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun finishTask(request: FinishTaskRequest): Either<Failure, String> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.finishTask(request)) { it.data }
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