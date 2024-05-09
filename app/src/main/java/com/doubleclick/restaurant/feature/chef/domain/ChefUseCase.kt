package com.doubleclick.restaurant.feature.chef.domain

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.chef.ChefRepository
import com.doubleclick.restaurant.feature.chef.domain.model.Data
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import javax.inject.Inject



class ChefUseCase @Inject constructor(private val repository: ChefRepository) :
    UseCase<List<Data>, Status>() {

    override suspend fun run(params: Status): Either<Failure, List<Data>> = repository.getOrdersChef(params)


}