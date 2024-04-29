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
import retrofit2.Retrofit
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopService @Inject constructor(retrofit: Retrofit) : ShopApi {

    private val shopApi by lazy { retrofit.create(ShopApi::class.java) }
    override suspend fun allProducts(page: Int): Response<DataWrapper<ProductData>> = shopApi.allProducts(page)

    override suspend fun allProductsSorted(page: Int, orderBy: String, orderType: String): Response<DataWrapper<ProductData>> = shopApi.allProductsSorted(page, orderBy, orderType)

    override suspend fun showProduct(id: String): Response<DataWrapper<Product>> = shopApi.showProduct(id)

    override suspend fun filterProduct(
        search: String,
        origins: List<Int>,
        scaScoreFrom: String,
        scaScoreTo: String,
        providers: List<String>,
        altitudeFrom: String,
        altitudeTo: String,
        page: Int
    ): Response<DataWrapper<ProductData>> =
        shopApi.filterProduct(search, origins, scaScoreFrom, scaScoreTo, providers, altitudeFrom, altitudeTo, page)

    override suspend fun getCart(page: Int): Response<DataWrapper<CartData>> = shopApi.getCart(page)
    override suspend fun putCartWithOrWithoutOffer(request: PutCartRequest, offerId: String?): Response<DataWrapper<StoreData>> =
        shopApi.putCartWithOrWithoutOffer(request, offerId)

    override suspend fun updateCart(
        cartId: Float?, request: UpdateCartRequest
    ): Response<DataWrapper<UpdateCart>> = shopApi.updateCart(cartId, request)

    override suspend fun deleteCart(cartId: Float?): Response<DataWrapper<Unit>> = shopApi.deleteCart(cartId)

    override suspend fun getOrderDetails(providerId: String): Response<DataWrapper<OrderDetailsData>> = shopApi.getOrderDetails(providerId)

    override suspend fun getCoffeeShops(zip: String, providerId: String): Response<DataWrapper<List<CoffeeShop>>> =
        shopApi.getCoffeeShops(zip, providerId)

    override suspend fun listLockers(zip: String): Response<DataWrapper<List<LockerData>>> =
        shopApi.listLockers(zip)

    override suspend fun getCities(zip: String): Response<DataWrapper<List<FilterCitiesData>>> = shopApi.getCities(zip)

    override suspend fun getProviders(page: Int): Response<DataWrapper<ProvidersData>> = shopApi.getProviders(page)

    override suspend fun getOrigins(page: Int): Response<DataWrapper<OriginsData>> = shopApi.getOrigins(page)

    override suspend fun showCart(id: String): Response<DataWrapper<NewCart>> = shopApi.showCart(id)


    override suspend fun payOrder(@Body request: PayOrderRequest): Response<DataWrapper<PayOrderData>> =
        shopApi.payOrder(request)

    override suspend fun payOrderGooglePay(@Body request: PayOrderGooglePayRequest): Response<DataWrapper<PayOrderGooglePayData>> =
        shopApi.payOrderGooglePay(request)

    override suspend fun showOffer(id: String): Response<DataWrapper<ShowOfferData>> = shopApi.showOffer(id)


}