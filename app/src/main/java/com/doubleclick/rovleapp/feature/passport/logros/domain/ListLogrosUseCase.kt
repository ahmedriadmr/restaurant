package com.doubleclick.restaurant.feature.passport.logros.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.passport.PassportRepository
import com.doubleclick.restaurant.feature.passport.logros.data.newResponse.NewLogrosData
import javax.inject.Inject

class ListLogrosUseCase @Inject constructor(private val repository: PassportRepository) :
    UseCase<NewLogrosData, UseCase.None>() {

    override suspend fun run(params: None) = repository.listLogros()


}