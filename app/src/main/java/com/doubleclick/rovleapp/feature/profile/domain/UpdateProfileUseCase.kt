package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.showProfile.ProfileDetails
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val updateProfileRepository: ProfileRepository) :
    UseCase<ProfileDetails, UpdateProfileUseCase.Params>() {

    override suspend fun run(params: Params) = updateProfileRepository.updateProfile(
        params.name,
        params.phone,
        params.password,
        params.address,
        params.zip,
        params.cardId,
        params.countryId,
        params.provinceId,
        params.cityId,
        params.image,
        params.email
    )

    data class Params(
        val name: RequestBody?,
        val phone: RequestBody?,
        val password: RequestBody?,
        val address: RequestBody?,
        val zip: RequestBody?,
        val cardId: RequestBody?,
        val countryId: RequestBody?,
        val provinceId: RequestBody?,
        val cityId: RequestBody?,
        val image: MultipartBody.Part?,
        val email: RequestBody?
    )
}