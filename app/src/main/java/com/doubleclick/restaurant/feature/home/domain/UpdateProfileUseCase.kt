package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.UpdateProfileResponse
import okhttp3.RequestBody
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<UpdateProfileResponse, UpdateProfileUseCase.Params>() {

    override suspend fun run(params: Params) = repository.updateProfile(
        params.firstName,
        params.lastName,
        params.email,
        params.password,
        params.passwordConfirmation,
        params.phone,
        params.address
    )

    data class Params(
        val firstName: RequestBody?,
        val lastName: RequestBody?,
        val email: RequestBody?,
        val password: RequestBody?,
        val passwordConfirmation: RequestBody?,
        val phone: RequestBody?,
        val address: RequestBody?
    )
}