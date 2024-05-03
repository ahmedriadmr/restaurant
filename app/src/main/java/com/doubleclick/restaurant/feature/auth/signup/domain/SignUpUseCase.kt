package com.doubleclick.restaurant.feature.auth.signup.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.auth.data.AuthRepository
import com.doubleclick.restaurant.feature.auth.signup.data.request.SignUpRequest
import javax.inject.Inject

class SignUpUseCase
@Inject constructor(private val authRepository: AuthRepository) :
    UseCase<SignedUpUser, SignUpUseCase.Params>() {

    override suspend fun run(params: Params) = authRepository.signup(params.request)

    data class Params(val request: SignUpRequest)
}