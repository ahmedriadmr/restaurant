package com.doubleclick.rovleapp.feature.auth.login.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.auth.data.AuthRepository
import com.doubleclick.rovleapp.feature.auth.login.data.request.LoginRequest
import com.doubleclick.rovleapp.feature.auth.login.data.response.User
import javax.inject.Inject


class LoginUseCase
@Inject constructor(private val authRepository: AuthRepository) :
    UseCase<User, LoginUseCase.Params>() {

    override suspend fun run(params: Params) = authRepository.login(params.request)

    data class Params(val request: LoginRequest)
}
