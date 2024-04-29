package com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription

import androidx.lifecycle.viewModelScope
import com.doubleclick.rovleapp.core.platform.BaseViewModel
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.request.PaySubscriptionGooglePayRequest
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.response.PaySubscriptionGooglePayData
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.request.PaySubscriptionRequest
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.response.PaySubscriptionData
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.domain.PaySubscriptionGooglePayUseCase
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.domain.PaySubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaySubscriptionViewModel @Inject constructor(
    val appSettingsSource: AppSettingsSource,
    private val paySubscriptionUseCase: PaySubscriptionUseCase,
    private val paySubscriptionGooglePayUseCase: PaySubscriptionGooglePayUseCase
) : BaseViewModel() {



    private val _paySubscription: Channel<PaySubscriptionData> = Channel()
    val paySubscription: Flow<PaySubscriptionData> = _paySubscription.receiveAsFlow()

    fun doPaySubscription(subscription_id: String, card_number: String, card_expiration_date: String, card_cvx: String) =
        paySubscriptionUseCase(PaySubscriptionUseCase.Params(PaySubscriptionRequest(subscription_id, card_number, card_expiration_date,card_cvx)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleRate) }

    private fun handleRate(data: PaySubscriptionData) {
        viewModelScope.launch { _paySubscription.send(data) }
    }



    private val _paySubscriptionGooglePay: Channel<PaySubscriptionGooglePayData> = Channel()
    val paySubscriptionGooglePay: Flow<PaySubscriptionGooglePayData> = _paySubscriptionGooglePay.receiveAsFlow()

    fun doPaySubscriptionGooglePay(
        request: PaySubscriptionGooglePayRequest
    ) = paySubscriptionGooglePayUseCase(
        PaySubscriptionGooglePayUseCase.Params(
            request
        ), viewModelScope, this
    ) { it.fold(::handleFailure, ::handlePaySubscriptionGooglePay) }

    private fun handlePaySubscriptionGooglePay(data: PaySubscriptionGooglePayData) {
        viewModelScope.launch { _paySubscriptionGooglePay.send(data) }
    }
}