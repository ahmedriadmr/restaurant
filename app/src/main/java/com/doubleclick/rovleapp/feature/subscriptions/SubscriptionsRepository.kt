package com.doubleclick.restaurant.feature.subscriptions

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.NetworkHandler
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.listSubscriptions.MySubscriptionsData
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.googlePay.request.PaySubscriptionGooglePayRequest
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.googlePay.response.PaySubscriptionGooglePayData
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.request.PaySubscriptionRequest
import com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.response.PaySubscriptionData
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.listPlans.PlansData
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan.PlanDetails
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.subscribe.request.SubscribeRequest
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface SubscriptionsRepository {
    suspend fun listPlans(providerId: String): Either<Failure, List<PlansData>>
    suspend fun showPlan(id: String): Either<Failure, PlanDetails>

    suspend fun filterPlan(
        name: String?,
        weightScoreFrom: String?,
        weightScoreTo: String?,
        providers: List<String>,
        priceFrom: String?,
        priceTo: String?
    ): Either<Failure, List<PlansData>>

    suspend fun listSubscriptions(): Either<Failure, List<MySubscriptionsData>>
    suspend fun showSubscription(id: String): Either<Failure, SubscriptionData>

    suspend fun doSubscribe(request: SubscribeRequest): Either<Failure, SubscriptionData>

    suspend fun cancelSubscription(id: String): Either<Failure, String>

    suspend fun pauseSubscription(id: String, status: String, activationDate: String): Either<Failure, SubscriptionData>
    suspend fun editSubscription(
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
    ): Either<Failure, SubscriptionData>


    suspend fun paySubscription(request: PaySubscriptionRequest): Either<Failure, PaySubscriptionData>

    suspend fun paySubscriptionGooglePay(request: PaySubscriptionGooglePayRequest): Either<Failure, PaySubscriptionGooglePayData>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler, private val service: SubscriptionsService
    ) : SubscriptionsRepository {
        override suspend fun listPlans(providerId: String): Either<Failure, List<PlansData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.listPlans(providerId)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun showPlan(id: String): Either<Failure, PlanDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showPlan(id)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun filterPlan(
            name: String?,
            weightScoreFrom: String?,
            weightScoreTo: String?,
            providers: List<String>,
            priceFrom: String?,
            priceTo: String?
        ): Either<Failure, List<PlansData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.filterPlan(
                        name,
                        weightScoreFrom,
                        weightScoreTo,
                        providers,
                        priceFrom,
                        priceTo
                    )
                ) { it.data }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun listSubscriptions(): Either<Failure, List<MySubscriptionsData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.listSubscriptions()) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun showSubscription(id: String): Either<Failure, SubscriptionData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showSubscription(id)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun doSubscribe(request: SubscribeRequest): Either<Failure, SubscriptionData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.doSubscribe(request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun cancelSubscription(id: String): Either<Failure, String> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.cancelSubscription(id)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun pauseSubscription(
            id: String,
            status: String,
            activationDate: String
        ): Either<Failure, SubscriptionData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.pauseSubscription(id, status , activationDate)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

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
        ): Either<Failure, SubscriptionData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.editSubscription(
                        id, periodicity, planId, sizeId, notes, deliveryType, zipCode, name, phone, email, shipping, coffeeShopId, lockerLocation, address
                    )
                ) { it.data }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun paySubscription(request: PaySubscriptionRequest): Either<Failure, PaySubscriptionData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.paySubscription(request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun paySubscriptionGooglePay(request: PaySubscriptionGooglePayRequest): Either<Failure, PaySubscriptionGooglePayData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.paySubscriptionGooglePay(request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        private fun <T, R> request(response: Response<T>, transform: (T) -> R): Either<Failure, R> {
            return try {
                when (response.isSuccessful && response.body() != null) {
                    true -> Either.Success(transform((response.body()!!)))
                    false ->  when (response.code()) {
                        404 -> Either.Failure(Failure.ServerError)
                        401 -> Either.Failure(Failure.Authentication)
                        else -> Either.Failure(Failure.FeatureFailure(response.errorBody()?.string()?.let { JSONObject(it).getString("message") }))
                    }

                }
            } catch (exception: Throwable) {
                Either.Failure(Failure.UnExpectedError(exception.message))
            }
        }
    }
}