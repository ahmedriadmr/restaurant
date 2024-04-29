package com.doubleclick.rovleapp.feature.auth.signup.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.auth.data.AuthRepository
import com.doubleclick.rovleapp.feature.auth.signup.data.request.SignUpRequest
import com.doubleclick.rovleapp.feature.auth.signup.data.response.UserData
import javax.inject.Inject

class SignUpUseCase
@Inject constructor(private val authRepository: AuthRepository) :
    UseCase<UserData, SignUpUseCase.Params>() {

    override suspend fun run(params: Params) = authRepository.signup(params.request)

    data class Params(val request: SignUpRequest)
}