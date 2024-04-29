package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val logoutRepository: ProfileRepository) :
    UseCase<String, UseCase.None>() {

    override suspend fun run(params: None) = logoutRepository.logout()

}