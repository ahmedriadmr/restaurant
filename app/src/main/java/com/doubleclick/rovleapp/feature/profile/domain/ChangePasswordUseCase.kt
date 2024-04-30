package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.changePassword.ChangePassword
import com.doubleclick.restaurant.feature.profile.data.showProfile.ProfileDetails
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(private val updateProfileRepository: ProfileRepository) : UseCase<ProfileDetails, ChangePasswordUseCase.Params>() {

    override suspend fun run(params: Params) = updateProfileRepository.changePassword(
        params.request
    )

    data class Params(
        val request: ChangePassword,

        )
}