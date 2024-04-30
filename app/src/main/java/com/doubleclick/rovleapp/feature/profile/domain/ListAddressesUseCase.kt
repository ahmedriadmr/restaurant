package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.listAddresses.AddressesData
import javax.inject.Inject

class ListAddressesUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<List<AddressesData>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getAddresses()

}