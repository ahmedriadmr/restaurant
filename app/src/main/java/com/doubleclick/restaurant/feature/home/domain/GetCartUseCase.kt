package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import javax.inject.Inject

class GetCartUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<List<CartData>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getCart()


}