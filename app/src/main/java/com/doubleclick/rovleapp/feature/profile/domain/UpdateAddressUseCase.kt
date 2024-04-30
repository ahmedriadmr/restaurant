package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.updateAddress.UpdateAddressData
import javax.inject.Inject

class UpdateAddressUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<UpdateAddressData, UpdateAddressUseCase.Params>() {

    override suspend fun run(params: Params) =
        repository.updateAddress(params.addressId, params.countryId, params.provinceId, params.cityId, params.address, params.zip, params.userName, params.email, params.phone)

    data class Params(val addressId: String,val countryId: String,val provinceId: String,val cityId: String,val address: String,val zip: String,val userName: String, val email: String, val phone: String)
}