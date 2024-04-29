package com.doubleclick.rovleapp.feature.auth.data

import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.request.forgetPassword.ForgetPasswordRequest
import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.request.resetPassword.ResetPasswordRequest
import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.request.verifyOtp.VerifyOtpRequest
import com.doubleclick.rovleapp.feature.auth.login.data.request.LoginRequest
import com.doubleclick.rovleapp.feature.auth.signup.data.request.SignUpRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService
@Inject constructor(retrofit: Retrofit) : AuthApi {
    private val authApi by lazy { retrofit.create(AuthApi::class.java) }

    override suspend fun login(request: LoginRequest) = authApi.login(request)
    override suspend fun signup(request: SignUpRequest) = authApi.signup(request)
    override suspend fun forgetPassword(request: ForgetPasswordRequest) = authApi.forgetPassword(request)
    override suspend fun verifyOtp(request: VerifyOtpRequest) = authApi.verifyOtp(request)
    override suspend fun resetPassword(request: ResetPasswordRequest) = authApi.resetPassword(request)
}
