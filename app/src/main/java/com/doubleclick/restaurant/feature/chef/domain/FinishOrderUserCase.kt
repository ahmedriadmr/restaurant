package com.doubleclick.restaurant.feature.chef.domain

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.chef.ChefRepository
import com.doubleclick.restaurant.feature.chef.domain.model.Data
import com.doubleclick.restaurant.feature.chef.domain.model.Message
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import javax.inject.Inject

class FinishOrderUserCase @Inject constructor(private val repository: ChefRepository) :
    UseCase<Message, String>() {
    override suspend fun run(params: String): Either<Failure, Message> = repository.finished(params)

}