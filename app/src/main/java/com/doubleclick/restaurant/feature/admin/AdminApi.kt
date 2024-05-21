package com.doubleclick.restaurant.feature.admin

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.AddProductRequest
import com.doubleclick.restaurant.feature.admin.data.addProduct.response.AddProductResponse
import com.doubleclick.restaurant.feature.admin.data.addStaff.request.AddStaffRequest
import com.doubleclick.restaurant.feature.admin.data.addStaff.response.AddStaffData
import com.doubleclick.restaurant.feature.admin.data.listItems.ItemsData
import com.doubleclick.restaurant.feature.admin.data.listStaff.UsersData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdminApi {
    companion object {
        private const val ITEMS = "items"
        private const val USERS = "users"

    }

    @GET(ITEMS)
    suspend fun getItems(): Response<DataWrapper<List<ItemsData>>>


    @GET(USERS)
    suspend fun getUsers(): Response<DataWrapper<List<UsersData>>>

    @POST(USERS)
    suspend fun addStaff(@Body request: AddStaffRequest) : Response<DataWrapper<AddStaffData>>

    @POST(ITEMS)
    suspend fun addProduct(@Body request: AddProductRequest) : Response<AddProductResponse>
}
