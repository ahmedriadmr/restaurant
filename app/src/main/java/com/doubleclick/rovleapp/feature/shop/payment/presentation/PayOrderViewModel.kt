package com.doubleclick.restaurant.feature.shop.payment.presentation

import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.shop.payment.data.googlePay.request.PayOrderGooglePayRequest
import com.doubleclick.restaurant.feature.shop.payment.data.googlePay.response.PayOrderGooglePayData
import com.doubleclick.restaurant.feature.shop.payment.data.request.PayOrderRequest
import com.doubleclick.restaurant.feature.shop.payment.data.response.PayOrderData
import com.doubleclick.restaurant.feature.shop.payment.domain.PayOrderGooglePayUseCase
import com.doubleclick.restaurant.feature.shop.payment.domain.PayOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayOrderViewModel @Inject constructor(
    val appSettingsSource: AppSettingsSource,
    private val payOrderUseCase: PayOrderUseCase,
    private val payOrderGooglePayUseCase: PayOrderGooglePayUseCase
) : BaseViewModel() {


    private val _payOrder: Channel<PayOrderData> = Channel()
    val payOrder: Flow<PayOrderData> = _payOrder.receiveAsFlow()

    fun doPayOrder(
        card_number: String,
        card_expiration_date: String,
        card_cvx: String,
        provider_id: String,
        delivery_type: String,
        zip_code: String,
        name: String,
        phone: String,
        email: String,
        address: String,
        coffee_shop_id: String,
        locker_location: String,
        note: String,
        payment_method: String
    ) = payOrderUseCase(
        PayOrderUseCase.Params(
            PayOrderRequest(
                card_number,
                card_expiration_date,
                card_cvx,
                provider_id,
                delivery_type,
                zip_code,
                name,
                phone,
                email,
                address,
                coffee_shop_id,
                locker_location,
                note,
                payment_method
            )
        ), viewModelScope, this
    ) { it.fold(::handleFailure, ::handlePayOrder) }

    private fun handlePayOrder(data: PayOrderData) {
        viewModelScope.launch { _payOrder.send(data) }
    }


    private val _payOrderGooglePay: Channel<PayOrderGooglePayData> = Channel()
    val payOrderGooglePay: Flow<PayOrderGooglePayData> = _payOrderGooglePay.receiveAsFlow()

    fun doPayOrderGooglePay(
        request: PayOrderGooglePayRequest
    ) = payOrderGooglePayUseCase(
        PayOrderGooglePayUseCase.Params(
            request
        ), viewModelScope, this
    ) { it.fold(::handleFailure, ::handlePayOrderGooglePay) }

    private fun handlePayOrderGooglePay(data: PayOrderGooglePayData) {
        viewModelScope.launch { _payOrderGooglePay.send(data) }
    }
}