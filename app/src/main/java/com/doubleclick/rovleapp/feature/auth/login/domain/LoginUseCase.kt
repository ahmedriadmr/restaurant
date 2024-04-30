package com.doubleclick.restaurant.feature.auth.login.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.auth.data.AuthRepository
import com.doubleclick.restaurant.feature.auth.login.data.request.LoginRequest
import com.doubleclick.restaurant.feature.auth.login.data.response.User
import javax.inject.Inject


class LoginUseCase
@Inject constructor(private val authRepository: AuthRepository) :
    UseCase<User, LoginUseCase.Params>() {

    override suspend fun run(params: Params) = authRepository.login(params.request)

    data class Params(val request: LoginRequest)
}
