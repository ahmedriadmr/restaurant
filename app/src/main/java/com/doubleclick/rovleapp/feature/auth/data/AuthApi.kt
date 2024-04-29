package com.doubleclick.rovleapp.feature.auth.data

import com.doubleclick.rovleapp.core.functional.DataWrapper
import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.ForgetPasswordResponse
import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.request.forgetPassword.ForgetPasswordRequest
import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.request.resetPassword.ResetPasswordRequest
import com.doubleclick.rovleapp.feature.auth.forgetPassword.data.request.verifyOtp.VerifyOtpRequest
import com.doubleclick.rovleapp.feature.auth.login.data.request.LoginRequest
import com.doubleclick.rovleapp.feature.auth.login.data.response.LoginRes
import com.doubleclick.rovleapp.feature.auth.login.data.response.User
import com.doubleclick.rovleapp.feature.auth.signup.data.request.SignUpRequest
import com.doubleclick.rovleapp.feature.auth.signup.data.response.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    companion object {
        private const val LOGIN = "auth/login"
        private const val SIGNUP = "auth/register"
        private const val forgetPassword = "auth/forgot-password"
        private const val verifyOtp = "auth/verify-otp"
        private const val resetPassword = "auth/reset-password"
    }

    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginRes<User>>

    @POST(SIGNUP)
    suspend fun signup(@Body request: SignUpRequest): Response<DataWrapper<UserData>>

    @POST(forgetPassword)
    suspend fun forgetPassword(@Body request: ForgetPasswordRequest): Response<ForgetPasswordResponse>

    @POST(verifyOtp)
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<ForgetPasswordResponse>

    @POST(resetPassword)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ForgetPasswordResponse>
}
