package com.doubleclick.restaurant.feature.admin.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.admin.AdminRepository
import com.doubleclick.restaurant.feature.admin.data.addStaff.request.AddStaffRequest
import com.doubleclick.restaurant.feature.admin.data.addStaff.response.AddStaffData
import javax.inject.Inject

class AddStaffUseCase @Inject constructor(private val repository: AdminRepository) :
    UseCase<AddStaffData, AddStaffUseCase.Params>() {

    override suspend fun run(params: Params) = repository.addStaff(params.request)

    data class Params(val request: AddStaffRequest)
}