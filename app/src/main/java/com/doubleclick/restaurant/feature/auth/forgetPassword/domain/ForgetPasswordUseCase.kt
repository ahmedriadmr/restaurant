package com.doubleclick.restaurant.feature.auth.forgetPassword.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.auth.data.AuthRepository
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.forgetPassword.ForgetPasswordRequest
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<ForgetPasswordResponse, ForgetPasswordUseCase.Params>() {

    override suspend fun run(params: Params) = authRepository.forgetPassword(params.request)

    data class Params(val request: ForgetPasswordRequest)
}