package com.doubleclick.restaurant.feature.admin

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.NetworkHandler
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.admin.data.listItems.ItemsData
import com.doubleclick.restaurant.feature.admin.data.listStaff.UsersData
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface AdminRepository {

    suspend fun getItems(): Either<Failure, List<ItemsData>>

    suspend fun getUsers(): Either<Failure, List<UsersData>>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: AdminService,
        private val appSettingsSource: AppSettingsSource
    ) : AdminRepository {
        override suspend fun getItems(): Either<Failure, List<ItemsData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getItems()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getUsers(): Either<Failure, List<UsersData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getUsers()) { it.data }
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