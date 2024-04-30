package com.doubleclick.restaurant.feature.subscriptions.providerList.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.profile.data.addAddress.request.AddAddressRequest
import com.doubleclick.restaurant.feature.profile.data.addAddress.response.AddressData
import com.doubleclick.restaurant.feature.profile.data.listAddresses.AddressesData
import com.doubleclick.restaurant.feature.profile.data.showProfile.ProfileDetails
import com.doubleclick.restaurant.feature.profile.domain.AddAddressUseCase
import com.doubleclick.restaurant.feature.profile.domain.ListAddressesUseCase
import com.doubleclick.restaurant.feature.profile.domain.ShowProfileUseCase
import com.doubleclick.restaurant.feature.profile.presentation.ProfileViewModel
import com.doubleclick.restaurant.feature.shop.cart.locker.data.LockerData
import com.doubleclick.restaurant.feature.shop.cart.locker.domain.ListLockersUseCase
import com.doubleclick.restaurant.feature.shop.cart.usecase.GetCoffeeShopUseCase
import com.doubleclick.restaurant.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import com.doubleclick.restaurant.feature.shop.cartDetails.domain.CitiesUseCase
import com.doubleclick.restaurant.feature.shop.response.CoffeeShop
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.domain.DoEditSubscriptionUseCase
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.domain.ShowSubscriptionUseCase
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.presentation.ShowSubscriptionViewModel
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan.PlanDetails
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.subscribe.request.SubscribeRequest
import com.doubleclick.restaurant.feature.subscriptions.providerList.domain.DoSubscribeUseCase
import com.doubleclick.restaurant.feature.subscriptions.providerList.domain.ShowPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowPlanViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val showPlanUseCase: ShowPlanUseCase,
    private val showSubscriptionUseCase: ShowSubscriptionUseCase,
    private val doSubscribeUseCase: DoSubscribeUseCase,
    private val doEditSubscriptionUseCase: DoEditSubscriptionUseCase,
    private val getCoffeeShopUseCase: GetCoffeeShopUseCase,
    private val listLockersUseCase: ListLockersUseCase,
    val appSettingsSource: AppSettingsSource,
    private val listAddressesUseCase: ListAddressesUseCase,
    private val citiesUseCase: CitiesUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val showProfileUseCase: ShowProfileUseCase
) :
    BaseViewModel() {

    companion object {
        const val showPlanKey = "SHOWPLAN"
        const val coffeeShopKey = "COFFEE_SHOP_KEY"
        const val lockersKey = "LOCKERS_KEY"
        const val addressesKey = "ADDRESSES_KEY"
        const val citiesKey = "CITIES_KEY"
    }

    private val _showPlan: MutableStateFlow<PlanDetails?> = MutableStateFlow(savedStateHandle[showPlanKey])
    val showPlan: StateFlow<PlanDetails?> = _showPlan

    fun getShowPlan(id: String) = showPlanUseCase(
        ShowPlanUseCase.Params(id),
        viewModelScope, this) { it.fold(::handleFailure, ::handleShowPlan) }

    private fun handleShowPlan(data: PlanDetails) {
        with(data) {
            savedStateHandle[showPlanKey] = this
            _showPlan.value = this
        }
    }


    private val _doSubscribe: Channel<SubscriptionData> = Channel()
    val doSubscribe: Flow<SubscriptionData> = _doSubscribe.receiveAsFlow()

    fun doSubscribe(
        periodicity: String,
        plan_id: String,
        size_id: Int,
        delivery_type: String,
        zip_code: String,
        name: String,
        phone: String,
        email: String,
        address: String,
        notes: String,
        coffee_shop_id: String,
        locker_location: String
    ) {
        doSubscribeUseCase(
            DoSubscribeUseCase.Params(
                SubscribeRequest(
                    periodicity,
                    plan_id,
                    size_id,
                    delivery_type,
                    zip_code,
                    name,
                    phone,
                    email,
                    address,
                    notes,
                    coffee_shop_id,
                    locker_location
                )
            ), viewModelScope, this) { it.fold(::handleFailure, ::handleSubscribe) }
    }

    private fun handleSubscribe(data: SubscriptionData) {
        viewModelScope.launch { _doSubscribe.send(data) }
    }


    private val _editSubscription: Channel<SubscriptionData> = Channel()
    val editSubscription: Flow<SubscriptionData> = _editSubscription.receiveAsFlow()

    fun doEditSubscribe(
        id: String,
        periodicity: String,
        plan_id: String,
        size_id: Int,
        notes: String,
        delivery_type: String,
        zip_code: String,
        name: String,
        phone: String,
        email: String,
        shipping: String,
        coffee_shop_id: String,
        locker_location: String,
        address: String
    ) {
        doEditSubscriptionUseCase(
            DoEditSubscriptionUseCase.Params(
                id,
                periodicity,
                plan_id,
                size_id,
                notes,
                delivery_type,
                zip_code,
                name,
                phone,
                email,
                shipping,
                coffee_shop_id,
                locker_location,
                address

            ), viewModelScope, this) { it.fold(::handleFailure, ::handleEditSubscribe) }
    }

    private fun handleEditSubscribe(data: SubscriptionData) {
        viewModelScope.launch { _editSubscription.send(data) }
    }


    private val _showSubscription: MutableStateFlow<SubscriptionData?> =
        MutableStateFlow(savedStateHandle[ShowSubscriptionViewModel.showSubscriptionKey])
    val showSubscription: StateFlow<SubscriptionData?> = _showSubscription

    fun getShowSubscription(id: String) = showSubscriptionUseCase(
        ShowSubscriptionUseCase.Params(id),
        viewModelScope, this) { it.fold(::handleFailure, ::handleShowSubscription) }

    private fun handleShowSubscription(data: SubscriptionData) {
        with(data) {
            savedStateHandle[ShowSubscriptionViewModel.showSubscriptionKey] = this
            _showSubscription.value = this
        }
    }

    private val _getCoffeeShop: MutableLiveData<List<CoffeeShop>> = MutableLiveData()
    val getCoffeeShop: LiveData<List<CoffeeShop>> get() = _getCoffeeShop

    fun getCoffeeShop(zip: String,providerId: String) {
        getCoffeeShopUseCase(GetCoffeeShopUseCase.Params(zip,providerId), viewModelScope, this) {
            it.fold(::handleFailure, ::handleCoffeeShop)
        }
    }

    private fun handleCoffeeShop(data: List<CoffeeShop>) {
        with(data) {
            savedStateHandle[coffeeShopKey] = this
            _getCoffeeShop.value = this
        }
    }
    fun clearCoffeeShop(){
        savedStateHandle[coffeeShopKey] = null
        _getCoffeeShop.value = emptyList()
    }


    private val _listLockers: MutableLiveData<List<LockerData>> = MutableLiveData()
    val listLockers: LiveData<List<LockerData>> get() = _listLockers

    fun getLockers(zip: String) {
        listLockersUseCase(ListLockersUseCase.Params(zip), viewModelScope, this) {
            it.fold(::handleFailure, ::handleLockers)
        }
    }

    fun clearLockers() {
        savedStateHandle[lockersKey] = null
        _listLockers.value = emptyList()
    }

    private fun handleLockers(data: List<LockerData>) {
        with(data) {
            savedStateHandle[lockersKey] = this
            _listLockers.value = this
        }
    }

    private val _getAddresses: MutableStateFlow<List<AddressesData>> = MutableStateFlow(savedStateHandle[addressesKey] ?: emptyList())
    val getAddresses: StateFlow<List<AddressesData>> = _getAddresses

    fun doGetAddresses() {
        listAddressesUseCase(UseCase.None(), viewModelScope, this) {
            it.fold(::handleFailure, ::handleGetAddresses)
        }
    }

    private fun handleGetAddresses(data: List<AddressesData>) {
        with(data) {
            savedStateHandle[ProfileViewModel.addressesKey] = this
            _getAddresses.value = this
        }
    }



    private val _getCities: MutableStateFlow<List<FilterCitiesData>> = MutableStateFlow(savedStateHandle[citiesKey] ?: emptyList())
    val getCities: StateFlow<List<FilterCitiesData>> = _getCities

    fun getCities(zip: String) {
        citiesUseCase(CitiesUseCase.Params(zip), viewModelScope, this) {
            it.fold(::handleFailure, ::handleGetCities)
        }
    }
    fun clearCities() {
        savedStateHandle[citiesKey] = null
        _getCities.value = emptyList()
    }
    private fun handleGetCities(data: List<FilterCitiesData>) {
        with(data) {
            savedStateHandle[citiesKey] = this
            _getCities.value = this
        }
    }

    private val _addAddress: Channel<AddressData> = Channel()
    val addAddress: Flow<AddressData> = _addAddress.receiveAsFlow()

    fun doAddAddress(country_id: String, province_id: String, city_id: String, address: String, zip: String, userName: String, email: String, phone: String) =
        addAddressUseCase(AddAddressUseCase.Params(AddAddressRequest(country_id, province_id, city_id, address, zip, userName, email, phone)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleAddAddress) }

    private fun handleAddAddress(data: AddressData) {
        viewModelScope.launch { _addAddress.send(data) }
    }


    private val _showProfile: MutableStateFlow<ProfileDetails?> = MutableStateFlow(savedStateHandle[ProfileViewModel.showProfileKey])
    val showProfile: StateFlow<ProfileDetails?> = _showProfile

    fun showProfile() {
        showProfileUseCase(
            UseCase.None(), viewModelScope, this) { it.fold(::handleFailure, ::handleShowProfile) }
    }

    private fun handleShowProfile(data: ProfileDetails) {
        with(data) {
            savedStateHandle[ProfileViewModel.showProfileKey] = this
            _showProfile.value = this
        }
    }
}