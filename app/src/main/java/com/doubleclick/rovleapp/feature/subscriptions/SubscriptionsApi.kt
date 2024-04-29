package com.doubleclick.rovleapp.feature.subscriptions

import com.doubleclick.rovleapp.core.functional.DataWrapper
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.listSubscriptions.MySubscriptionsData
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.request.PaySubscriptionGooglePayRequest
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.response.PaySubscriptionGooglePayData
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.request.PaySubscriptionRequest
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.response.PaySubscriptionData
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.listPlans.PlansData
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan.PlanDetails
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.subscribe.request.SubscribeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SubscriptionsApi {

    companion object {
        private const val Plans = "plans"
        private const val Plan = "plans/{id}"
        private const val PlansFilter = "plans/filter"
        private const val mySubscriptions = "subscriptions"
        private const val Subscription = "subscriptions/{id}"
        private const val doSubscribe = "subscriptions"
        private const val cancelSubscription = "subscriptions/{id}"
        private const val pauseSubscription = "subscriptions/{id}/change-status"
        private const val editSubscription = "subscriptions/{id}"
        private const val paySubscription = "https://rovle.eslamghazy.net/api/mangopay/payments/subscription"
        private const val paySubscriptionGooglePay = "https://rovle.eslamghazy.net/api/v2/payments/verify"

    }

    @GET(Plans)
    suspend fun listPlans(@Query("provider_id") providerId: String): Response<DataWrapper<List<PlansData>>>

    @GET(Plan)
    suspend fun showPlan(@Path("id") id: String): Response<DataWrapper<PlanDetails>>
    @GET(PlansFilter)
    suspend fun filterPlan(
        @Query("name") name: String?,
        @Query("weight_from") weightScoreFrom: String?,
        @Query("weight_to") weightScoreTo: String?,
        @Query("providers[]") providers: List<String>,
        @Query("price_from") priceFrom: String?,
        @Query("price_to") priceTo: String?
    ): Response<DataWrapper<List<PlansData>>>

    @GET(mySubscriptions)
    suspend fun listSubscriptions(): Response<DataWrapper<List<MySubscriptionsData>>>

    @GET(Subscription)
    suspend fun showSubscription(@Path("id") id: String): Response<DataWrapper<SubscriptionData>>

    @POST(doSubscribe)
    suspend fun doSubscribe(@Body request: SubscribeRequest): Response<DataWrapper<SubscriptionData>>

    @DELETE(cancelSubscription)
    suspend fun cancelSubscription(@Path("id") id: String): Response<DataWrapper<String>>

    @PUT(pauseSubscription)
    @FormUrlEncoded
    suspend fun pauseSubscription(
        @Path("id") id: String,
        @Field("status") status: String,
        @Field("activation_date") activationDate: String
    ): Response<DataWrapper<SubscriptionData>>

    @PUT(editSubscription)
    @FormUrlEncoded
    suspend fun editSubscription(
        @Path("id") id: String,
        @Field("periodicity") periodicity: String,
        @Field("plan_id") planId: String,
        @Field("size_id") sizeId: Int,
        @Field("notes") notes: String,
        @Field("delivery_type") deliveryType: String,
        @Field("zip_code") zipCode: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("shipping") shipping: String,
        @Field("coffee_shop_id") coffeeShopId: String,
        @Field("locker_location") lockerLocation: String,
        @Field("address") address: String
    ): Response<DataWrapper<SubscriptionData>>


    @POST(paySubscription)
    suspend fun paySubscription(@Body request: PaySubscriptionRequest): Response<DataWrapper<PaySubscriptionData>>

    @POST(paySubscriptionGooglePay)
    suspend fun paySubscriptionGooglePay(@Body request: PaySubscriptionGooglePayRequest): Response<DataWrapper<PaySubscriptionGooglePayData>>

}