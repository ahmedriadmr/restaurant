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
import retrofit2.Retrofit
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionsService @Inject constructor(retrofit: Retrofit) : SubscriptionsApi {

    private val subscriptionsApi by lazy { retrofit.create(SubscriptionsApi::class.java) }
    override suspend fun listPlans(providerId: String): Response<DataWrapper<List<PlansData>>> =
        subscriptionsApi.listPlans(providerId)

    override suspend fun showPlan(id: String): Response<DataWrapper<PlanDetails>> =
        subscriptionsApi.showPlan(id)
    override suspend fun filterPlan(
        name: String?,
        weightScoreFrom: String?,
        weightScoreTo: String?,
        providers: List<String>,
        priceFrom: String?,
        priceTo: String?
    ): Response<DataWrapper<List<PlansData>>> =
        subscriptionsApi.filterPlan(name, weightScoreFrom, weightScoreTo, providers, priceFrom, priceTo)
    override suspend fun listSubscriptions(): Response<DataWrapper<List<MySubscriptionsData>>> =
        subscriptionsApi.listSubscriptions()

    override suspend fun showSubscription(id: String): Response<DataWrapper<SubscriptionData>> =
        subscriptionsApi.showSubscription(id)

    override suspend fun doSubscribe(request: SubscribeRequest) = subscriptionsApi.doSubscribe(request)

    override suspend fun cancelSubscription(id: String): Response<DataWrapper<String>> = subscriptionsApi.cancelSubscription(id)

    override suspend fun pauseSubscription(
        id: String, status: String ,activationDate: String
    ): Response<DataWrapper<SubscriptionData>> = subscriptionsApi.pauseSubscription(id, status, activationDate)

    override suspend fun editSubscription(
        id: String,
        periodicity: String,
        planId: String,
        sizeId: Int,
        notes: String,
        deliveryType: String,
        zipCode: String,
        name: String,
        phone: String,
        email: String,
        shipping: String,
        coffeeShopId: String,
        lockerLocation: String,
        address: String
    ) = subscriptionsApi.editSubscription(
        id, periodicity, planId, sizeId, notes, deliveryType, zipCode, name, phone, email, shipping, coffeeShopId, lockerLocation, address
    )


    override suspend fun paySubscription(@Body request: PaySubscriptionRequest): Response<DataWrapper<PaySubscriptionData>> =
        subscriptionsApi.paySubscription(request)

    override suspend fun paySubscriptionGooglePay(@Body request: PaySubscriptionGooglePayRequest): Response<DataWrapper<PaySubscriptionGooglePayData>> =
        subscriptionsApi.paySubscriptionGooglePay(request)
}