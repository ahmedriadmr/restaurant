package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.listAddresses.AddressesData
import javax.inject.Inject

class ListAddressesUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<List<AddressesData>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getAddresses()

}