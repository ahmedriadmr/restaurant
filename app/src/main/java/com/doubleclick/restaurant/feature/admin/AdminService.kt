package com.doubleclick.restaurant.feature.admin

import com.doubleclick.restaurant.feature.admin.data.addStaff.request.AddStaffRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class AdminService @Inject constructor(retrofit: Retrofit) : AdminApi {
    private val adminApi by lazy { retrofit.create(AdminApi::class.java) }

    override suspend fun getItems() = adminApi.getItems()

    override suspend fun getUsers() = adminApi.getUsers()
    override suspend fun addStaff(request: AddStaffRequest) =adminApi.addStaff(request)

}
