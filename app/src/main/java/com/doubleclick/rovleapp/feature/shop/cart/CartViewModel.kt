package com.doubleclick.restaurant.feature.shop.cart

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
import com.doubleclick.restaurant.feature.shop.cart.response.getCart.CartData
import com.doubleclick.restaurant.feature.shop.cart.response.getCart.NewCart
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.UpdateCart
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.shop.cart.usecase.DeleteCartUseCase
import com.doubleclick.restaurant.feature.shop.cart.usecase.GetCartUseCase
import com.doubleclick.restaurant.feature.shop.cart.usecase.GetCoffeeShopUseCase
import com.doubleclick.restaurant.feature.shop.cart.usecase.UpdateCartUseCase
import com.doubleclick.restaurant.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import com.doubleclick.restaurant.feature.shop.cartDetails.data.orderDetails.OrderDetailsData
import com.doubleclick.restaurant.feature.shop.cartDetails.domain.CitiesUseCase
import com.doubleclick.restaurant.feature.shop.cartDetails.domain.OrderDetailsUseCase
import com.doubleclick.restaurant.feature.shop.cartDetails.domain.ShowCartUseCase
import com.doubleclick.restaurant.feature.shop.response.CoffeeShop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCartUseCase: GetCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val orderDetailsUseCase: OrderDetailsUseCase,
    private val getCoffeeShopUseCase: GetCoffeeShopUseCase,
    private val listLockersUseCase: ListLockersUseCase,
    private val showCartUseCase: ShowCartUseCase,
    val appSettingsSource: AppSettingsSource,
    private val listAddressesUseCase: ListAddressesUseCase,
    private val citiesUseCase: CitiesUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val showProfileUseCase: ShowProfileUseCase
) : BaseViewModel() {

    companion object {
        const val cartKey = "CART_KEY"
        const val coffeeShopKey = "COFFEE_SHOP_KEY"
        const val lockersKey = "LOCKERS_KEY"
        const val orderDetailsKey = "ORDER_DETAILS_KEY"
        const val addressesKey = "ADDRESSES_KEY"
        const val citiesKey = "CITIES_KEY"
    }
    fun resetCartPage() {
        cartPage = 1
    }
    private var cartPage = 1
    private val _getCart: MutableStateFlow<List<NewCart>> = MutableStateFlow(savedStateHandle[cartKey] ?: emptyList())
    val getCart: StateFlow<List<NewCart>> = _getCart
    var adapterPosition = -1

    fun getCart() {
        getCartUseCase(GetCartUseCase.Params(cartPage), viewModelScope) {
            it.fold(::handleFailure, ::handleGetCart)
        }
    }

    private fun handleGetCart(data: CartData) {
        cartPage++
        cartQueryPageSize = data.per_page
        cartIsLastPage = data.last_page == data.current_page
        cartOldList.clear()
        cartOldList.addAll(data.data)
        val newList = mutableListOf<NewCart>()
        newList.addAll(cartOldList)
        _getCart.value = newList
        savedStateHandle[cartKey] = newList
        val dataList = data.data
        var totalNumberOfCartItems = 0
        for (newCart in dataList) {
            totalNumberOfCartItems += newCart.cart_items.size
        }
        viewModelScope.launch { appSettingsSource.setCartInventory(totalNumberOfCartItems) }
    }

    private var cartOldList: MutableList<NewCart> = mutableListOf()

    var cartQueryPageSize = 1
    var cartIsLastPage = false

    private val _updateCart: Channel<UpdateCart> = Channel()
    val updateCart: Flow<UpdateCart> = _updateCart.receiveAsFlow()

    fun updateCart(cartId: Float?, request: UpdateCartRequest) {
        updateCartUseCase(
            UpdateCartUseCase.Params(cartId, request),
            viewModelScope, this) { it.fold(::handleFailure, ::handleUpdateCart) }
    }

    private fun handleUpdateCart(data: UpdateCart) {
        viewModelScope.launch { _updateCart.send(data) }
    }


    private val _deleteCart: Channel<Unit> = Channel()
    val deleteCart: Flow<Unit> = _deleteCart.receiveAsFlow()

    fun deleteCart(cartId: Float?) {
        deleteCartUseCase(
            DeleteCartUseCase.Params(cartId),
            viewModelScope, this) { it.fold(::handleFailure, ::handleDeleteCart) }
    }

    private fun handleDeleteCart(@Suppress("UNUSED_PARAMETER") data: Unit) {
        viewModelScope.launch {
            cartPage = 1
            cartOldList = mutableListOf()
            _deleteCart.send(Unit)

        }
    }


    private val _orderDetails: MutableStateFlow<OrderDetailsData?> = MutableStateFlow(savedStateHandle[orderDetailsKey])
    val orderDetails: StateFlow<OrderDetailsData?> = _orderDetails

    fun getOrderDetails(providerId: String) = orderDetailsUseCase(
        OrderDetailsUseCase.Params(providerId),
        viewModelScope, this) { it.fold(::handleFailure, ::handleOrderDetails) }

    private fun handleOrderDetails(data: OrderDetailsData) {
        with(data) {
            savedStateHandle[orderDetailsKey] = this
            _orderDetails.value = this
        }
    }

    private val _getCoffeeShop: MutableLiveData<List<CoffeeShop>> = MutableLiveData()
    val getCoffeeShop: LiveData<List<CoffeeShop>> get() = _getCoffeeShop

    fun getCoffeeShop(zip: String, providerId: String) {
        getCoffeeShopUseCase(GetCoffeeShopUseCase.Params(zip, providerId), viewModelScope, this) {
            it.fold(::handleFailure, ::handleCoffeeShop)
        }
    }

    fun clearCoffeeShop() {
        savedStateHandle[coffeeShopKey] = null
        _getCoffeeShop.value = emptyList()
    }

    private fun handleCoffeeShop(data: List<CoffeeShop>) {
        savedStateHandle[coffeeShopKey] = data
        _getCoffeeShop.value = data
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


    private val _showCart: Channel<NewCart?> = Channel()
    val showCart: Flow<NewCart?> = _showCart.receiveAsFlow()

    fun getShowCart(id: String) = showCartUseCase(
        ShowCartUseCase.Params(id),
        viewModelScope, this) { it.fold(::handleFailure, ::handleShowCart) }

    private fun handleShowCart(data: NewCart) {
        viewModelScope.launch { _showCart.send((data)) }

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
            savedStateHandle[addressesKey] = this
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