package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.addAddress.request.AddAddressRequest
import com.doubleclick.rovleapp.feature.profile.data.addAddress.response.AddressData
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(private val addAddressRepository: ProfileRepository) :
    UseCase<AddressData, AddAddressUseCase.Params>() {

    override suspend fun run(params: Params) = addAddressRepository.addAddress(
        params.request
    )

    data class Params(
        val request: AddAddressRequest
    )
}