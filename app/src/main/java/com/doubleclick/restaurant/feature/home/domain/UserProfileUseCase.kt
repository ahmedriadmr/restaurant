package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.userProfile.UserProfileData
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<UserProfileData, UseCase.None>() {

    override suspend fun run(params: None) = repository.userProfile()


}