package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<String, DeleteAddressUseCase.Params>() {

    override suspend fun run(params: Params) = repository.deleteAddress(params.addressId)

    data class Params(val addressId: String)
}