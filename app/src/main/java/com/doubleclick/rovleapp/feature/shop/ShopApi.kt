package com.doubleclick.rovleapp.feature.shop

import com.doubleclick.rovleapp.core.functional.DataWrapper
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface ShopApi {
    companion object {
        private const val AllProducts = "https://rovle.eslamghazy.net/api/v2/products"
        private const val showProduct = "products/{id}"
        private const val searchProduct = "https://rovle.eslamghazy.net/api/v2/products/filter"
        private const val CART = "https://rovle.eslamghazy.net/api/v2/cart"
        private const val CoffeeShops = "coffee-shops"
        private const val orderDetails = "https://rovle.eslamghazy.net/api/v2/provider/{id}/order-details"
        private const val Providers = "providers"
        private const val Origins = "origins"
        private const val payOrderByVisa = "https://rovle.eslamghazy.net/api/mangopay/payments/order"
        private const val OFFERS = "https://rovle.eslamghazy.net/api/v1/passport/offers"
        private const val filterCities = "cities/filter"
        private const val locker = "https://rovle.eslamghazy.net/api/correos"
        private const val payOrderGooglePay = "https://rovle.eslamghazy.net/api/v2/payments/verify"
    }

    @GET(AllProducts)
    suspend fun allProducts(
        @Query("page") page: Int
    ): Response<DataWrapper<ProductData>>

    @GET(AllProducts)
    suspend fun allProductsSorted(
        @Query("page") page: Int,
        @Query("order_by") orderBy: String,
        @Query("order_type") orderType: String
    ): Response<DataWrapper<ProductData>>

    @GET(showProduct)
    suspend fun showProduct(@Path("id") id: String): Response<DataWrapper<Product>>

    @GET(searchProduct)
    suspend fun filterProduct(
        @Query("search") search: String,
        @Query("origins[]") origins: List<Int>,
        @Query("sca_score_from") scaScoreFrom: String,
        @Query("sca_score_to") scaScoreTo: String,
        @Query("providers[]") providers: List<String>,
        @Query("altitude_from") altitudeFrom: String,
        @Query("altitude_to") altitudeTo: String,
        @Query("page") page: Int
    ): Response<DataWrapper<ProductData>>

    @POST(CART)
    suspend fun putCartWithOrWithoutOffer(@Body request: PutCartRequest, @Query("offer_id") offerId: String?): Response<DataWrapper<StoreData>>

    @GET(CART)
    suspend fun getCart(@Query("page") page: Int): Response<DataWrapper<CartData>>

    @PUT("$CART/{id}")
    suspend fun updateCart(
        @Path("id") cartId: Float?,
        @Body request: UpdateCartRequest
    ): Response<DataWrapper<UpdateCart>>


    @DELETE("$CART/{id}")
    suspend fun deleteCart(@Path("id") cartId: Float?): Response<DataWrapper<Unit>>

    @GET(orderDetails)
    suspend fun getOrderDetails(@Path("id") providerId: String): Response<DataWrapper<OrderDetailsData>>

    @GET(CoffeeShops)
    suspend fun getCoffeeShops(
        @Query("zip") zip: String,
        @Query("provider_id") providerId: String
    ): Response<DataWrapper<List<CoffeeShop>>>

    @GET(locker)
    suspend fun listLockers(@Query("zip") zip: String): Response<DataWrapper<List<LockerData>>>

    @GET(filterCities)
    suspend fun getCities(@Query("zip") zip: String): Response<DataWrapper<List<FilterCitiesData>>>

    @GET(Providers)
    suspend fun getProviders(@Query("page") page: Int): Response<DataWrapper<ProvidersData>>

    @GET(Origins)
    suspend fun getOrigins(@Query("page") page: Int): Response<DataWrapper<OriginsData>>

    @GET("$CART/{id}")
    suspend fun showCart(@Path("id") id: String): Response<DataWrapper<NewCart>>


    @POST(payOrderByVisa)
    suspend fun payOrder(@Body request: PayOrderRequest): Response<DataWrapper<PayOrderData>>

    @POST(payOrderGooglePay)
    suspend fun payOrderGooglePay(@Body request: PayOrderGooglePayRequest): Response<DataWrapper<PayOrderGooglePayData>>

    @GET("$OFFERS/{id}")
    suspend fun showOffer(@Path("id") id: String): Response<DataWrapper<ShowOfferData>>


}