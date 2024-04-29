package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.showProfile.ProfileDetails
import javax.inject.Inject

class ShowProfileUseCase @Inject constructor(private val showProfileRepository: ProfileRepository) :
    UseCase<ProfileDetails, UseCase.None>() {

    override suspend fun run(params: None) = showProfileRepository.showProfile()

}