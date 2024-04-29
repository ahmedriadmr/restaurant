package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.presentation

import androidx.lifecycle.viewModelScope
import com.doubleclick.rovleapp.core.platform.BaseViewModel
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain.DoCancelSubscriptionUseCase
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain.DoInitSubscriptionStatus
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain.ShowSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowSubscriptionViewModel @Inject constructor(
    private val showSubscriptionUseCase: ShowSubscriptionUseCase,
    private val doCancelSubscriptionUseCase: DoCancelSubscriptionUseCase,
    private val doInitSubscriptionStatus: DoInitSubscriptionStatus,
    val appSettingsSource: AppSettingsSource
) :
    BaseViewModel() {

    companion object {
        const val showSubscriptionKey = "SHOWSUBSCRIPTION"
    }

    private val _showSubscription: Channel<SubscriptionData> = Channel()
    val showSubscription: Flow<SubscriptionData> = _showSubscription.receiveAsFlow()

    fun getShowSubscription(id: String) = showSubscriptionUseCase(
        ShowSubscriptionUseCase.Params(id),
        viewModelScope, this) { it.fold(::handleFailure, ::handleShowSubscription) }

    private fun handleShowSubscription(data: SubscriptionData) {
        viewModelScope.launch { _showSubscription.send(data) }
    }


    private val _cancel: Channel<String> = Channel()
    val cancel: Flow<String> = _cancel.receiveAsFlow()

    fun docancel(id: String) = doCancelSubscriptionUseCase(
        DoCancelSubscriptionUseCase.Params(id), viewModelScope, this) { it.fold(::handleFailure, ::handleCancel) }

    private fun handleCancel(data: String) {
        viewModelScope.launch { _cancel.send(data) }
    }


    private val _pause: Channel<SubscriptionData> = Channel()
    val pause: Flow<SubscriptionData> = _pause.receiveAsFlow()

    fun doInitStatus(id: String, status: String, activationDate: String) = doInitSubscriptionStatus(
        DoInitSubscriptionStatus.Params(id, status, activationDate), viewModelScope, this) { it.fold(::handleFailure, ::handlePause) }

    private fun handlePause(data: SubscriptionData) {
        viewModelScope.launch { _pause.send(data) }
    }


}