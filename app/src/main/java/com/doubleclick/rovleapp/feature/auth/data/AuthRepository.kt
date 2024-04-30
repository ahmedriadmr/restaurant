package com.doubleclick.restaurant.feature.auth.data

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.NetworkHandler
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.forgetPassword.ForgetPasswordRequest
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.resetPassword.ResetPasswordRequest
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.verifyOtp.VerifyOtpRequest
import com.doubleclick.restaurant.feature.auth.login.data.request.LoginRequest
import com.doubleclick.restaurant.feature.auth.login.data.response.LoginRes
import com.doubleclick.restaurant.feature.auth.login.data.response.User
import com.doubleclick.restaurant.feature.auth.signup.data.request.SignUpRequest
import com.doubleclick.restaurant.feature.auth.signup.data.response.UserData
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(request: LoginRequest): Either<Failure, User>
    suspend fun signup(request: SignUpRequest): Either<Failure, UserData>
    suspend fun forgetPassword(request: ForgetPasswordRequest): Either<Failure, ForgetPasswordResponse>
    suspend fun verifyOtp(request: VerifyOtpRequest): Either<Failure, ForgetPasswordResponse>
    suspend fun resetPassword(request: ResetPasswordRequest): Either<Failure, ForgetPasswordResponse>
    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: AuthService,
        private val appSettingsSource: AppSettingsSource,
    ) : AuthRepository {
        override suspend fun login(request: LoginRequest): Either<Failure, User> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.login(request)) {
                    localSource(it)
                    it.user
                }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun signup(request: SignUpRequest): Either<Failure, UserData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.signup(request)) {
                    localSourceSignUp(it)
                    it.data
                }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun forgetPassword(request: ForgetPasswordRequest): Either<Failure, ForgetPasswordResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.forgetPassword(request)) { it }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun verifyOtp(request: VerifyOtpRequest): Either<Failure, ForgetPasswordResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.verifyOtp(request)) { it}
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }
        override suspend fun resetPassword(request: ResetPasswordRequest): Either<Failure, ForgetPasswordResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.resetPassword(request)) { it }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        private fun localSource(model: LoginRes<User>) {
            runBlocking {
                model.let { data ->
                    appSettingsSource.saveUserAccess(
                        UserAccess(
                            address = model.user.address,
                            cardId = model.user.card_id,
                            cityId = model.user.city_id,
                            countryId = model.user.country_id,
                            email = model.user.email,
                            hasDashboardAccess = model.user.has_dashboard_access,
                            id = model.user.id,
                            image = model.user.image,
                            levelId = model.user.level_id,
                            name = model.user.name,
                            phone = data.user.phone,
                            provinceId = data.user.province_id,
                            token = data.token,
                            zip = data.user.zip
                        )
                    )
                }
            }
        }

        private fun localSourceSignUp(model: DataWrapper<UserData>) {
            runBlocking {
                model.let { data ->
                    appSettingsSource.saveUserAccess(
                        UserAccess(
                            address = model.data.data.address,
                            cardId = model.data.data.card_id,
                            cityId = model.data.data.city_id,
                            countryId = model.data.data.country_id,
                            email = model.data.data.email,
                            hasDashboardAccess = model.data.data.has_dashboard_access,
                            id = model.data.data.id,
                            image = model.data.data.image,
                            levelId = model.data.data.level_id,
                            name = model.data.data.name,
                            phone = data.data.data.phone,
                            provinceId = data.data.data.province_id,
                            token = data.token,
                            zip = data.data.data.zip
                        )
                    )
                }
            }
        }


        private fun <T, R> request(response: Response<T>, transform: (T) -> R): Either<Failure, R> {
            return try {
                when {
                    response.isSuccessful && response.body() != null -> {
                        Either.Success(transform(response.body()!!))
                    }
                    response.code() == 404 || response.code() == 500 -> {
                        Either.Failure(Failure.ServerError)
                    }
                    response.code() == 401 || response.code() == 422 -> {
                        val errorBody = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorBody?.getString("message")

                        if (errorMessage == "Validation error" || errorMessage == "validation error") {
                            val userEmailErrors = errorBody.getJSONObject("errors").optJSONArray("user_email")
                            val userPasswordErrors = errorBody.getJSONObject("errors").optJSONArray("user_password")
                            val userEmailLogin = errorBody.getJSONObject("errors").optJSONArray("email")
                            when {
                                (userEmailErrors?.length() ?: 0) > 0 -> {
                                    Either.Failure(Failure.FeatureFailure(userEmailErrors?.getString(0)))
                                }
                                (userPasswordErrors?.length() ?: 0) > 0 -> {
                                    Either.Failure(Failure.FeatureFailure(userPasswordErrors?.getString(0)))
                                }
                                (userEmailLogin?.length() ?: 0) > 0 -> {
                                    Either.Failure(Failure.FeatureFailure(userEmailLogin?.getString(0)))
                                }
                                else -> Either.Failure(Failure.FeatureFailure(errorMessage))
                            }
                        } else {
                            Either.Failure(Failure.FeatureFailure(errorMessage))
                        }
                    }
                    else -> Either.Failure(Failure.FeatureFailure(response.errorBody()?.string()?.let { JSONObject(it).getString("message") }))
                }
            } catch (exception: Throwable) {
                Either.Failure(Failure.UnExpectedError(exception.message))
            }
        }
    }
}
