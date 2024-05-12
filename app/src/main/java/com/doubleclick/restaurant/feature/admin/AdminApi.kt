package com.doubleclick.restaurant.feature.admin

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.admin.data.listItems.ItemsData
import com.doubleclick.restaurant.feature.admin.data.listStaff.UsersData
import retrofit2.Response
import retrofit2.http.GET

interface AdminApi {
    companion object {
        private const val ITEMS = "items"
        private const val USERS = "users"

    }

    @GET(ITEMS)
    suspend fun getItems(): Response<DataWrapper<List<ItemsData>>>


    @GET(USERS)
    suspend fun getUsers(): Response<DataWrapper<List<UsersData>>>
}
