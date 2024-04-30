package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.addAddress.request.AddAddressRequest
import com.doubleclick.restaurant.feature.profile.data.addAddress.response.AddressData
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