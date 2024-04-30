package com.doubleclick.restaurant.feature.shop.cart.locker.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.shop.ShopRepository
import com.doubleclick.restaurant.feature.shop.cart.locker.data.LockerData
import javax.inject.Inject

class ListLockersUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<List<LockerData>, ListLockersUseCase.Params>() {

    override suspend fun run(params: Params) =
        repository.listLockers(params.zip)

    data class Params(val zip: String)

}