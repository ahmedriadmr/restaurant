package com.doubleclick.restaurant.feature.auth.forgetPassword.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.auth.data.AuthRepository
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.verifyOtp.VerifyOtpRequest
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<ForgetPasswordResponse, VerifyOtpUseCase.Params>() {

    override suspend fun run(params: Params) = authRepository.verifyOtp(params.request)

    data class Params(val request: VerifyOtpRequest)
}