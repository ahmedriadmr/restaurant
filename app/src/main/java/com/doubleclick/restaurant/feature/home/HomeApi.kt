package com.doubleclick.restaurant.feature.home

import com.doubleclick.restaurant.core.functional.DataWrapper
import com.doubleclick.restaurant.feature.home.data.Categories
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    companion object {
        private const val categories = "categories"

    }

    @GET(categories)
    suspend fun getCategories(): Response<DataWrapper<List<Categories>>>


}
