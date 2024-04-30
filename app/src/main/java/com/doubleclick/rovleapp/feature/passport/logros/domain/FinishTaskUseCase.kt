package com.doubleclick.restaurant.feature.passport.logros.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.passport.PassportRepository
import com.doubleclick.restaurant.feature.passport.logros.data.finishTask.FinishTaskRequest
import javax.inject.Inject

class FinishTaskUseCase @Inject constructor(private val finishTaskRepository: PassportRepository) :
    UseCase<String, FinishTaskUseCase.Params>() {

    override suspend fun run(params: Params) = finishTaskRepository.finishTask(
        params.request
    )

    data class Params(
        val request: FinishTaskRequest
    )
}