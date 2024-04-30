package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.showProfile.ProfileDetails
import javax.inject.Inject

class ShowProfileUseCase @Inject constructor(private val showProfileRepository: ProfileRepository) :
    UseCase<ProfileDetails, UseCase.None>() {

    override suspend fun run(params: None) = showProfileRepository.showProfile()

}