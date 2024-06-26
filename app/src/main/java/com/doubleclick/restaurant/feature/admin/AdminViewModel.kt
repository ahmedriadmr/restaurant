package com.doubleclick.restaurant.feature.admin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.AddProductRequest
import com.doubleclick.restaurant.feature.admin.data.addProduct.response.AddProductResponse
import com.doubleclick.restaurant.feature.admin.data.addStaff.request.AddStaffRequest
import com.doubleclick.restaurant.feature.admin.data.addStaff.response.AddStaffData
import com.doubleclick.restaurant.feature.admin.data.listItems.ItemsData
import com.doubleclick.restaurant.feature.admin.data.listStaff.UsersData
import com.doubleclick.restaurant.feature.admin.domain.AddProductUseCase
import com.doubleclick.restaurant.feature.admin.domain.AddStaffUseCase
import com.doubleclick.restaurant.feature.admin.domain.GetItemsUseCase
import com.doubleclick.restaurant.feature.admin.domain.GetUsersUseCase
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
import com.doubleclick.restaurant.feature.home.domain.CategoriesUseCase
import com.doubleclick.restaurant.feature.home.domain.LogOutUseCase
import com.doubleclick.restaurant.feature.home.presentation.HomeViewModel
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val getItemsUseCase: GetItemsUseCase,
    val getUsersUSeCase: GetUsersUseCase,
    val addStaffUseCase: AddStaffUseCase,
    val addProductUseCase: AddProductUseCase,
    val getCategoriesUseCase: CategoriesUseCase,
    private val logoutUseCase: LogOutUseCase,
    val appSettingsSource: AppSettingsSource,
    val token: Task<String>
) :
    BaseViewModel() {

    companion object {
        const val newItemsKey = "ITEMS"
        const val USERSKEY = "Users"


    }

    fun token() = flow {
        if (token.isComplete) {
            emit(token.result)
        }
    }.flowOn(Dispatchers.IO)



    private val _listItems: MutableStateFlow<List<ItemsData>> =
        MutableStateFlow(savedStateHandle[newItemsKey] ?: emptyList())
    val listItems: StateFlow<List<ItemsData>> = _listItems

    fun getItems() {
        getItemsUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleListItems) }
    }

    private fun handleListItems(data: List<ItemsData>) {
        with(data) {
            savedStateHandle[newItemsKey] = this
            _listItems.value = this
        }
    }


    private val _listUsers: MutableStateFlow<List<UsersData>> =
        MutableStateFlow(savedStateHandle[USERSKEY] ?: emptyList())
    val listUsers: StateFlow<List<UsersData>> = _listUsers

    fun getUsers() {
        getUsersUSeCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleListUsers) }
    }

    private fun handleListUsers(data: List<UsersData>) {
        with(data) {
            savedStateHandle[USERSKEY] = this
            _listUsers.value = this
        }
    }


    private val _addStaff: Channel<AddStaffData> = Channel()
    val addStaff: Flow<AddStaffData> = _addStaff.receiveAsFlow()


    fun addStaff(request: AddStaffRequest) {
        addStaffUseCase(AddStaffUseCase.Params(request), viewModelScope, this) {
            it.fold(::handleFailure, ::handleAddStaff)
        }
    }

    private fun handleAddStaff(data: AddStaffData) {
        viewModelScope.launch {
            _addStaff.send(data)
        }
    }


    private val _addProduct: Channel<AddProductResponse> = Channel()
    val addProduct: Flow<AddProductResponse> = _addProduct.receiveAsFlow()


    fun addProduct(request: AddProductRequest) {
        addProductUseCase(AddProductUseCase.Params(request), viewModelScope, this) {
            it.fold(::handleFailure, ::handleAddProduct)
        }
    }

    private fun handleAddProduct(data: AddProductResponse) {
        viewModelScope.launch {
            _addProduct.send(data)
        }
    }

    private val _listCategories: MutableStateFlow<List<Categories>> =
        MutableStateFlow(savedStateHandle[HomeViewModel.newCategoriesKey] ?: emptyList())
    val listCategories: StateFlow<List<Categories>> = _listCategories

    fun getCategories() {
        getCategoriesUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleListCategories) }
    }

    private fun handleListCategories(data: List<Categories>) {
        with(data) {
            savedStateHandle[HomeViewModel.newCategoriesKey] = this
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

}