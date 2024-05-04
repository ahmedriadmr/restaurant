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
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.domain.CategoriesUseCase
import com.doubleclick.restaurant.feature.home.domain.GetCartUseCase
import com.doubleclick.restaurant.feature.home.domain.LogOutUseCase
import com.doubleclick.restaurant.feature.home.domain.PutCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val getCategoriesUseCase: CategoriesUseCase,
    private val logoutUseCase: LogOutUseCase,
    private val putCartUseCase: PutCartUseCase,
    val getCartUseCase: GetCartUseCase,
    val appSettingsSource: AppSettingsSource
) :
    BaseViewModel() {

    companion object {
        const val newCategoriesKey = "CATEGORIES"
        const val listCartDataKey = "CART"

    }

    private val _listCategories: MutableStateFlow<List<Categories>> =
        MutableStateFlow(savedStateHandle[newCategoriesKey] ?: emptyList())
    val listCategories: StateFlow<List<Categories>> = _listCategories

    fun getCategories() {
        getCategoriesUseCase(
            UseCase.None(), viewModelScope, this) { it.fold(::handleFailure, ::handleListCategories) }
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

    private val _listCart: MutableStateFlow<List<CartData>> =
        MutableStateFlow(savedStateHandle[listCartDataKey] ?: emptyList())
    val listCart: StateFlow<List<CartData>> = _listCart

    fun getCart() {
        getCartUseCase(
            UseCase.None(), viewModelScope, this) { it.fold(::handleFailure, ::handleListCart) }
    }

    private fun handleListCart(data: List<CartData>) {
        with(data) {
            savedStateHandle[listCartDataKey] = this
            _listCart.value = this
        }
    }
}