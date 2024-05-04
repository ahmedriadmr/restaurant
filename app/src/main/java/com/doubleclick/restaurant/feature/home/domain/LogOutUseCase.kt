package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<LogoutResponse, UseCase.None>() {

    override suspend fun run(params: None) = repository.logout()


}