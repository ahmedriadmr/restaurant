package com.doubleclick.rovleapp.feature.passport.logros.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.passport.PassportRepository
import com.doubleclick.rovleapp.feature.passport.logros.data.finishTask.FinishTaskRequest
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