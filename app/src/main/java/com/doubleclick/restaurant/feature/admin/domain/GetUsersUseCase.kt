package com.doubleclick.restaurant.feature.admin.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.admin.AdminRepository
import com.doubleclick.restaurant.feature.admin.data.listStaff.UsersData
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: AdminRepository) :
    UseCase<List<UsersData>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getUsers()


}