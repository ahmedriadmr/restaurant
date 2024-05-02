package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<String, UseCase.None>() {

    override suspend fun run(params: None) = repository.logout()


}