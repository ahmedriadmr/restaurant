package com.doubleclick.rovleapp.feature.passport.logros.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.passport.PassportRepository
import com.doubleclick.rovleapp.feature.passport.logros.data.newResponse.NewLogrosData
import javax.inject.Inject

class ListLogrosUseCase @Inject constructor(private val repository: PassportRepository) :
    UseCase<NewLogrosData, UseCase.None>() {

    override suspend fun run(params: None) = repository.listLogros()


}