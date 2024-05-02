package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.home.data.Categories
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface HomeApi {
    companion object {
        private const val categories = "categories"
        private const val logout = "logout"

    }

    @GET(categories)
    suspend fun getCategories(): Response<DataWrapper<List<Categories>>>

    @DELETE(logout)
    suspend fun logout(): Response<DataWrapper<String>>

}
