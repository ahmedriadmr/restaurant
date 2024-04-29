package com.doubleclick.rovleapp.feature.passport

import com.doubleclick.rovleapp.core.functional.DataWrapper
import com.doubleclick.rovleapp.feature.passport.logros.data.finishTask.FinishTaskRequest
import com.doubleclick.rovleapp.feature.passport.logros.data.newResponse.NewLogrosData
import com.doubleclick.rovleapp.feature.passport.offers.data.Offers
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PassportApi {
    companion object {
        private const val OFFERS = "passport/offers"
        private const val TASKS = "tasks"
    }

    @GET(OFFERS)
    suspend fun listOffers(): Response<DataWrapper<Offers>>

    @GET(TASKS)
    suspend fun listLogros(): Response<DataWrapper<NewLogrosData>>

    @POST(TASKS)
    suspend fun finishTask(@Body request: FinishTaskRequest): Response<DataWrapper<String>>
}