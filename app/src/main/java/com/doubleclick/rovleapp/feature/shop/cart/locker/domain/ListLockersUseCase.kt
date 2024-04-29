package com.doubleclick.rovleapp.feature.shop.cart.locker.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.shop.ShopRepository
import com.doubleclick.rovleapp.feature.shop.cart.locker.data.LockerData
import javax.inject.Inject

class ListLockersUseCase @Inject constructor(private val repository: ShopRepository) :
    UseCase<List<LockerData>, ListLockersUseCase.Params>() {

    override suspend fun run(params: Params) =
        repository.listLockers(params.zip)

    data class Params(val zip: String)

}