package com.doubleclick.restaurant.feature.auth.data

import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.forgetPassword.ForgetPasswordRequest
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.resetPassword.ResetPasswordRequest
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.verifyOtp.VerifyOtpRequest
import com.doubleclick.restaurant.feature.auth.login.data.request.LoginRequest
import com.doubleclick.restaurant.feature.auth.login.data.responseNew.LoginResponse
import com.doubleclick.restaurant.feature.auth.login.data.responseNew.NewUser
import com.doubleclick.restaurant.feature.auth.signup.data.request.SignUpRequest
import com.doubleclick.restaurant.feature.auth.signup.data.responseNew.SignUpResponse
import com.doubleclick.restaurant.feature.auth.signup.data.responseNew.SignedUpUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    companion object {
        private const val LOGIN = "login"
        private const val SIGNUP = "register"
        private const val FORGETPASSWORD = "send_otp"
        private const val VERIFYOTP = "confirm_code"
        private const val RESETPASSWORD = "reset-password"
    }

    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse<NewUser>>

    @POST(SIGNUP)
    suspend fun signup(@Body request: SignUpRequest): Response<SignUpResponse<SignedUpUser>>

    @POST(FORGETPASSWORD)
    suspend fun forgetPassword(@Body request: ForgetPasswordRequest): Response<ForgetPasswordResponse>

    @POST(VERIFYOTP)
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<ForgetPasswordResponse>

    @POST(RESETPASSWORD)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ForgetPasswordResponse>
}
