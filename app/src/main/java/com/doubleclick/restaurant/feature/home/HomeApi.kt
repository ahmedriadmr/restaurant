package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.home.data.Categories
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface HomeApi {
    companion object {
        private const val CATEGORIES = "all_categories"
        private const val LOGOUT = "logout"

    }

    @GET(CATEGORIES)
    suspend fun getCategories(): Response<DataWrapper<List<Categories>>>

    @DELETE(LOGOUT)
    suspend fun logout(): Response<DataWrapper<String>>

}
