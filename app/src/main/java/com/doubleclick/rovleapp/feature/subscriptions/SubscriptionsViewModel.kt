package com.doubleclick.rovleapp.feature.subscriptions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.core.platform.BaseViewModel
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.feature.shop.ShopViewModel
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.providers.Providers
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.providers.ProvidersData
import com.doubleclick.rovleapp.feature.shop.searchProduct.domain.GetProvidersUseCase
import com.doubleclick.rovleapp.feature.subscriptions.filterPlan.domain.FilterPlanUseCase
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.listSubscriptions.MySubscriptionsData
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.domain.ListSubscriptionsUseCase
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.listPlans.PlansData
import com.doubleclick.rovleapp.feature.subscriptions.providerList.domain.ListPlansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val listPlansUseCase: ListPlansUseCase,
    private val listSubscriptionsUseCase: ListSubscriptionsUseCase,
    val filterPlanUseCase: FilterPlanUseCase,
    private val getProvidersUseCase: GetProvidersUseCase,
    val appSettingsSource: AppSettingsSource
) : BaseViewModel() {
    var selected = ""
    companion object {
        const val listPlansKey = "LISTPLANS"
        const val filterKey = "FILTER_KEY"
    }

    private val _listPlans: MutableStateFlow<List<PlansData>> =
        MutableStateFlow(savedStateHandle[listPlansKey] ?: emptyList())
    val listPlans: StateFlow<List<PlansData>> = _listPlans

    fun listPlans(providerId: String) {
        listPlansUseCase(
            ListPlansUseCase.Params(providerId), viewModelScope, this) { it.fold(::handleFailure, ::handleListPlans) }
    }

    private fun handleListPlans(data: List<PlansData>) {
        with(data) {
            savedStateHandle[listPlansKey] = this
            _listPlans.value = this
        }
    }

    private val _listSubscriptions: Channel<List<MySubscriptionsData>>  = Channel()
    val listSubscriptions: Flow<List<MySubscriptionsData>> = _listSubscriptions.receiveAsFlow()

    fun listSubscriptions() {
        listSubscriptionsUseCase(
            UseCase.None(), viewModelScope, this) { it.fold(::handleFailure, ::handleListSubscriptions) }
    }

    private fun handleListSubscriptions(data: List<MySubscriptionsData>) {

        viewModelScope.launch { _listSubscriptions.send(data) }
    }

    /** Search **/
    private val _filterPlan: MutableStateFlow<List<PlansData>?> = MutableStateFlow(savedStateHandle[filterKey])
    val filterPlan: StateFlow<List<PlansData>?> = _filterPlan

    fun clearFilterPlan() {
        _filterPlan.value = null
    }

    fun filterPlan(
        name: String?,
        weightScoreFrom: String?,
        weightScoreTo: String?,
        providers: List<String>,
        priceFrom: String?,
        priceTo: String?
    ) {
        filterPlanUseCase(
            FilterPlanUseCase.Params(name, weightScoreFrom, weightScoreTo, providers, priceFrom, priceTo),
            viewModelScope, this) {
            it.fold(::handleFailure, ::handleSearchPlanResponse)
        }
    }

    private fun handleSearchPlanResponse(products: List<PlansData>) {
        with(products) {
            savedStateHandle[filterKey] = this
            _filterPlan.value = this
            _listPlans.value = this
        }
    }
    fun resetProvidersPage() {
        providersPage = 1
    }
    private var providersPage = 1
    private val _getProviders: MutableStateFlow<List<Providers>> = MutableStateFlow(savedStateHandle[ShopViewModel.providersKey] ?: emptyList())
    val getProviders: StateFlow<List<Providers>> = _getProviders

    fun getProviders() {
        getProvidersUseCase(GetProvidersUseCase.Params(providersPage), viewModelScope, this) {
            it.fold(::handleFailure, ::handleProviders)
        }
    }

    private fun handleProviders(data: ProvidersData) {
        providersPage++
        providersQUERYPAGESIZE = data.per_page
        providersIsLastPage = data.last_page == data.current_page
        providersOldList.clear()
        providersOldList.addAll(data.data)
        val newList = mutableListOf<Providers>()
        newList.addAll(providersOldList)
        _getProviders.value = newList
        savedStateHandle[ShopViewModel.providersKey] = newList
    }

    private val providersOldList = mutableListOf<Providers>()

    var providersQUERYPAGESIZE = 1
    var providersIsLastPage = false
}
