package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val logoutRepository: ProfileRepository) :
    UseCase<String, UseCase.None>() {

    override suspend fun run(params: None) = logoutRepository.logout()

}