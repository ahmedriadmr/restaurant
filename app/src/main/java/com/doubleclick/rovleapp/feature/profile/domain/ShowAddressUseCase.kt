package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.addAddress.response.AddressData
import javax.inject.Inject

class ShowAddressUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<AddressData, ShowAddressUseCase.Params>() {

    override suspend fun run(params: Params) = repository.showAddress(params.id)

    data class Params(val id: String)

}