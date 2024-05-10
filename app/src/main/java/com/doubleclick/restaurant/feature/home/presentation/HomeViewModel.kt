package com.doubleclick.restaurant.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.data.UpdateProfileResponse
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderRequest
import com.doubleclick.restaurant.feature.home.data.cancelOrder.CancelOrderResponse
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.makeOrder.request.MakeOrderRequest
import com.doubleclick.restaurant.feature.home.data.makeOrder.response.MakeOrderResponse
import com.doubleclick.restaurant.feature.home.data.searchOrders.request.SearchOrdersRequest
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.SearchOrdersData
import com.doubleclick.restaurant.feature.home.data.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.home.data.updateCart.response.UpdateCartResponse
import com.doubleclick.restaurant.feature.home.data.userProfile.UserProfileData
import com.doubleclick.restaurant.feature.home.domain.CancelOrderUseCase
import com.doubleclick.restaurant.feature.home.domain.CategoriesUseCase
import com.doubleclick.restaurant.feature.home.domain.GetCartUseCase
import com.doubleclick.restaurant.feature.home.domain.ListOrdersUseCase
import com.doubleclick.restaurant.feature.home.domain.LogOutUseCase
import com.doubleclick.restaurant.feature.home.domain.MakeOrderUseCase
import com.doubleclick.restaurant.feature.home.domain.PutCartUseCase
import com.doubleclick.restaurant.feature.home.domain.SearchOrdersUseCase
import com.doubleclick.restaurant.feature.home.domain.UpdateCartUseCase
import com.doubleclick.restaurant.feature.home.domain.UpdateProfileUseCase
import com.doubleclick.restaurant.feature.home.domain.UserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val getCategoriesUseCase: CategoriesUseCase,
    private val logoutUseCase: LogOutUseCase,
    private val putCartUseCase: PutCartUseCase,
    val updateCartUseCase: UpdateCartUseCase,
    val getCartUseCase: GetCartUseCase,
    val userProfileUseCase: UserProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val makeOrderUseCase: MakeOrderUseCase,
    val listOrdersUseCase: ListOrdersUseCase,
    val cancelOrderUseCase: CancelOrderUseCase,
    val searchOrdersUseCase: SearchOrdersUseCase,
    val appSettingsSource: AppSettingsSource
) :
    BaseViewModel() {

    companion object {
        const val newCategoriesKey = "CATEGORIES"
        const val listCartDataKey = "CART"
        const val listOrderDataKey = "ORDER"
        const val searchOrdersKey = "SEARCH ORDERS"

    }

    private val _listCategories: MutableStateFlow<List<Categories>> =
        MutableStateFlow(savedStateHandle[newCategoriesKey] ?: emptyList())
    val listCategories: StateFlow<List<Categories>> = _listCategories

    fun getCategories() {
        getCategoriesUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleListCategories) }
    }

    private fun handleListCategories(data: List<Categories>) {
        with(data) {
            savedStateHandle[newCategoriesKey] = this
            _listCategories.value = this
        }
    }

    private val _logout: Channel<LogoutResponse> = Channel()
    val logout: Flow<LogoutResponse> = _logout.receiveAsFlow()

    fun doLogout() {
        logoutUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleLogout) }
    }

    private fun handleLogout(data: LogoutResponse) {
        viewModelScope.launch { _logout.send(data) }
    }


    private val _putCart: Channel<PutCartResponse> = Channel()
    val putCart: Flow<PutCartResponse> = _putCart.receiveAsFlow()


    fun putCart(request: PutCartRequest) {
        putCartUseCase(PutCartUseCase.Params(request), viewModelScope, this) {
            it.fold(::handleFailure, ::handlePutCart)
        }
    }

    private fun handlePutCart(data: PutCartResponse) {
        viewModelScope.launch {
            _putCart.send(data)
        }
    }

    private val _updateCart: Channel<UpdateCartResponse> = Channel()
    val updateCart: Flow<UpdateCartResponse> = _updateCart.receiveAsFlow()


    fun updateCart(id: String, request: UpdateCartRequest) {
        updateCartUseCase(UpdateCartUseCase.Params(id, request), viewModelScope, this) {
            it.fold(::handleFailure, ::handleUpdateCart)
        }
    }

    private fun handleUpdateCart(data: UpdateCartResponse) {
        viewModelScope.launch {
            _updateCart.send(data)
        }
    }

    private val _listCart: MutableStateFlow<List<CartData>> =
        MutableStateFlow(savedStateHandle[listCartDataKey] ?: emptyList())
    val listCart: StateFlow<List<CartData>> = _listCart

    fun getCart() {
        getCartUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleListCart) }
    }

    private fun handleListCart(data: List<CartData>) {
        with(data) {
            savedStateHandle[listCartDataKey] = this
            _listCart.value = this
        }
    }

    private val _profile: Channel<UserProfileData> = Channel()
    val profile: Flow<UserProfileData> = _profile.receiveAsFlow()

    fun getUserProfile() {
        userProfileUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleUserProfile) }
    }

    private fun handleUserProfile(data: UserProfileData) {
        viewModelScope.launch { _profile.send(data) }
    }


    private val _updateProfile: Channel<UpdateProfileResponse> = Channel()
    val updateProfile: Flow<UpdateProfileResponse> = _updateProfile.receiveAsFlow()

    fun updateProfile(
        firstName: String, lastName: String, email: String, password: String?, passwordConfirmation: String?, phone: String,
        address: String
    ) {
        updateProfileUseCase(
            UpdateProfileUseCase.Params(
                initRequestBody(firstName),
                initRequestBody(lastName),
                initRequestBody(email),
                password?.let { initRequestBody(it) },
                passwordConfirmation?.let { initRequestBody(it) },
                initRequestBody(phone),
                initRequestBody(address),

                ), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleUpdateProfile) }
    }

    private fun initRequestBody(data: String) = data.toRequestBody(MultipartBody.FORM)

    private fun handleUpdateProfile(data: UpdateProfileResponse) {
        viewModelScope.launch { _updateProfile.send(data) }
    }


    private val _makeOrder: Channel<MakeOrderResponse> = Channel()
    val makeOrder: Flow<MakeOrderResponse> = _makeOrder.receiveAsFlow()


    fun makeOrder(request: MakeOrderRequest) {
        makeOrderUseCase(MakeOrderUseCase.Params(request), viewModelScope, this) {
            it.fold(::handleFailure, ::handleMakeOrder)
        }
    }

    private fun handleMakeOrder(data: MakeOrderResponse) {
        viewModelScope.launch {
            _makeOrder.send(data)
        }
    }

    private val _listOrders: MutableStateFlow<List<SearchOrdersData>> =
        MutableStateFlow(savedStateHandle[listOrderDataKey] ?: emptyList())
    val listOrders: StateFlow<List<SearchOrdersData>> = _listOrders

    fun getOrders() {
        listOrdersUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleListOrders) }
    }

    private fun handleListOrders(data: List<SearchOrdersData>) {
        with(data) {
            savedStateHandle[listOrderDataKey] = this
            _listOrders.value = this
        }
    }


    private val _cancelOrder: Channel<CancelOrderResponse> = Channel()
    val cancelOrder: Flow<CancelOrderResponse> = _cancelOrder.receiveAsFlow()


    fun cancelOrder(id: String, request: CancelOrderRequest) {
        cancelOrderUseCase(CancelOrderUseCase.Params(id, request), viewModelScope, this) {
            it.fold(::handleFailure, ::handleCancelOrder)
        }
    }

    private fun handleCancelOrder(data: CancelOrderResponse) {
        viewModelScope.launch {
            _cancelOrder.send(data)
        }
    }


    private val _searchOrders: MutableStateFlow<List<SearchOrdersData>> =
        MutableStateFlow(savedStateHandle[searchOrdersKey] ?: emptyList())
    val searchOrders: StateFlow<List<SearchOrdersData>> = _searchOrders

    fun searchOrders(request: SearchOrdersRequest) {
        searchOrdersUseCase(SearchOrdersUseCase.Params(request), viewModelScope, this) {
            it.fold(::handleFailure, ::handleSearchOrders)
        }
    }

    private fun handleSearchOrders(data: List<SearchOrdersData>) {
        with(data) {
            savedStateHandle[searchOrdersKey] = this
            _searchOrders.value = this
        }
    }
}