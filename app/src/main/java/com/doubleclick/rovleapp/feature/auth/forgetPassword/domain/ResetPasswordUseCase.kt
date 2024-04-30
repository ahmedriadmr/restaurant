package com.doubleclick.restaurant.feature.auth.forgetPassword.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.auth.data.AuthRepository
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.resetPassword.ResetPasswordRequest
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<ForgetPasswordResponse, ResetPasswordUseCase.Params>() {

    override suspend fun run(params: Params) = authRepository.resetPassword(params.request)

    data class Params(val request: ResetPasswordRequest)
}