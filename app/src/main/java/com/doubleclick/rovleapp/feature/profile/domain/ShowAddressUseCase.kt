package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.addAddress.response.AddressData
import javax.inject.Inject

class ShowAddressUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<AddressData, ShowAddressUseCase.Params>() {

    override suspend fun run(params: Params) = repository.showAddress(params.id)

    data class Params(val id: String)

}