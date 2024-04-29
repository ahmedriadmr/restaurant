package com.doubleclick.rovleapp.feature.shop

import com.doubleclick.rovleapp.core.exception.Failure
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.platform.NetworkHandler
import com.doubleclick.rovleapp.feature.shop.cart.locker.data.LockerData
import com.doubleclick.rovleapp.feature.shop.cart.request.putCart.PutCartRequest
import com.doubleclick.rovleapp.feature.shop.cart.response.getCart.CartData
import com.doubleclick.rovleapp.feature.shop.cart.response.getCart.NewCart
import com.doubleclick.rovleapp.feature.shop.cart.response.putCart.StoreData
import com.doubleclick.rovleapp.feature.shop.cart.response.updateCart.UpdateCart
import com.doubleclick.rovleapp.feature.shop.cart.response.updateCart.request.UpdateCartRequest
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails.OrderDetailsData
import com.doubleclick.rovleapp.feature.shop.payment.data.googlePay.request.PayOrderGooglePayRequest
import com.doubleclick.rovleapp.feature.shop.payment.data.googlePay.response.PayOrderGooglePayData
import com.doubleclick.rovleapp.feature.shop.payment.data.request.PayOrderRequest
import com.doubleclick.rovleapp.feature.shop.payment.data.response.PayOrderData
import com.doubleclick.rovleapp.feature.shop.response.CoffeeShop
import com.doubleclick.rovleapp.feature.shop.response.Product
import com.doubleclick.rovleapp.feature.shop.response.ProductData
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.origins.OriginsData
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.providers.ProvidersData
import com.doubleclick.rovleapp.feature.shop.showOffer.data.ShowOfferData
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

interface ShopRepository {
    suspend fun allProducts(page: Int): Either<Failure, ProductData>

    suspend fun allProductsSorted(page: Int, orderBy: String, orderType: String): Either<Failure, ProductData>

    suspend fun showProduct(id: String): Either<Failure, Product>

    suspend fun filterProduct(
        query: String,
        origins: List<Int>,
        scaScoreFrom: String,
        scaScoreTo: String,
        providers: List<String>,
        altitudeFrom: String,
        altitudeTo: String,
        page: Int
    ): Either<Failure, ProductData>

    suspend fun getCart(page: Int): Either<Failure, CartData>

    suspend fun putCartWithOrWithoutOffer(request: PutCartRequest, offerId: String?): Either<Failure, StoreData>
    suspend fun updateCart(
        cartId: Float?,
        request: UpdateCartRequest
    ): Either<Failure, UpdateCart>

    suspend fun deleteCart(cartId: Float?): Either<Failure, Unit>

    suspend fun getOrderDetails(providerId: String): Either<Failure, OrderDetailsData>

    suspend fun getCoffeeShops(zip: String, providerId: String): Either<Failure, List<CoffeeShop>>

    suspend fun listLockers(zip: String): Either<Failure, List<LockerData>>
    suspend fun getCities(zip: String): Either<Failure, List<FilterCitiesData>>
    suspend fun getProviders(page: Int): Either<Failure, ProvidersData>


    suspend fun getOrigins(page: Int): Either<Failure, OriginsData>

    suspend fun showCart(id: String): Either<Failure, NewCart>


    suspend fun payOrder(request: PayOrderRequest): Either<Failure, PayOrderData>

    suspend fun payOrderGooglePay(request: PayOrderGooglePayRequest): Either<Failure, PayOrderGooglePayData>

    suspend fun showOffer(id: String): Either<Failure, ShowOfferData>


    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: ShopService
    ) : ShopRepository {

        override suspend fun allProducts(page: Int): Either<Failure, ProductData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.allProducts(page)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun allProductsSorted(page: Int, orderBy: String, orderType: String): Either<Failure, ProductData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.allProductsSorted(page, orderBy, orderType)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun showProduct(id: String): Either<Failure, Product> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showProduct(id)) {
                    it.data
                }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun filterProduct(
            query: String,
            origins: List<Int>,
            scaScoreFrom: String,
            scaScoreTo: String,
            providers: List<String>,
            altitudeFrom: String,
            altitudeTo: String,
            page: Int
        ): Either<Failure, ProductData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.filterProduct(
                        query,
                        origins,
                        scaScoreFrom,
                        scaScoreTo,
                        providers,
                        altitudeFrom,
                        altitudeTo,
                        page
                    )
                ) { it.data }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun getCart(page: Int): Either<Failure, CartData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getCart(page)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun putCartWithOrWithoutOffer(request: PutCartRequest, offerId: String?): Either<Failure, StoreData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.putCartWithOrWithoutOffer(request, offerId)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun updateCart(
            cartId: Float?, request: UpdateCartRequest
        ): Either<Failure, UpdateCart> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.updateCart(cartId, request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun deleteCart(cartId: Float?): Either<Failure, Unit> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.deleteCart(cartId)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getOrderDetails(providerId: String): Either<Failure, OrderDetailsData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getOrderDetails(providerId)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getCoffeeShops(zip: String, providerId: String): Either<Failure, List<CoffeeShop>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getCoffeeShops(zip, providerId)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun listLockers(zip: String): Either<Failure, List<LockerData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.listLockers(zip)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getCities(zip: String): Either<Failure, List<FilterCitiesData>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getCities(zip)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getProviders(page: Int): Either<Failure, ProvidersData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getProviders(page)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun getOrigins(page: Int): Either<Failure, OriginsData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.getOrigins(page)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun showCart(id: String): Either<Failure, NewCart> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showCart(id)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        override suspend fun payOrder(request: PayOrderRequest): Either<Failure, PayOrderData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.payOrder(request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun payOrderGooglePay(request: PayOrderGooglePayRequest): Either<Failure, PayOrderGooglePayData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.payOrderGooglePay(request)) { it.data }
                false -> Either.Failure(Failure.NetworkConnection)
            }
        }

        override suspend fun showOffer(id: String): Either<Failure, ShowOfferData> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.showOffer(id)) {
                    it.data
                }

                false -> Either.Failure(Failure.NetworkConnection)
            }
        }


        private fun <T, R> request(response: Response<T>, transform: (T) -> R): Either<Failure, R> {
            return try {
                when (response.isSuccessful && response.body() != null) {
                    true -> Either.Success(transform((response.body()!!)))
                    false -> when (response.code()) {
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
