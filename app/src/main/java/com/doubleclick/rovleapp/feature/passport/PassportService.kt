package com.doubleclick.rovleapp.feature.passport

import com.doubleclick.rovleapp.core.functional.DataWrapper
import com.doubleclick.rovleapp.feature.passport.logros.data.finishTask.FinishTaskRequest
import com.doubleclick.rovleapp.feature.passport.logros.data.newResponse.NewLogrosData
import com.doubleclick.rovleapp.feature.passport.offers.data.Offers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PassportService @Inject constructor(retrofit: Retrofit) : PassportApi {

    private val passportApi by lazy { retrofit.create(PassportApi::class.java) }

    override suspend fun listOffers(): Response<DataWrapper<Offers>> = passportApi.listOffers()

    override suspend fun listLogros(): Response<DataWrapper<NewLogrosData>> =
        passportApi.listLogros()

    override suspend fun finishTask(@Body request: FinishTaskRequest): Response<DataWrapper<String>> =
        passportApi.finishTask(request)
}