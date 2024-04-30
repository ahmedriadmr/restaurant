package com.doubleclick.restaurant.feature.profile.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.profile.data.addAddress.request.AddAddressRequest
import com.doubleclick.restaurant.feature.profile.data.addAddress.response.AddressData
import com.doubleclick.restaurant.feature.profile.data.changePassword.ChangePassword
import com.doubleclick.restaurant.feature.profile.data.listAddresses.AddressesData
import com.doubleclick.restaurant.feature.profile.data.orders.listOrders.ListOrdersData
import com.doubleclick.restaurant.feature.profile.data.orders.listOrders.Order
import com.doubleclick.restaurant.feature.profile.data.orders.showOrder.ShowOrderData
import com.doubleclick.restaurant.feature.profile.data.points.PointsData
import com.doubleclick.restaurant.feature.profile.data.rates.RatingData
import com.doubleclick.restaurant.feature.profile.data.rates.RatingOrderRequest
import com.doubleclick.restaurant.feature.profile.data.showProfile.ProfileDetails
import com.doubleclick.restaurant.feature.profile.data.updateAddress.UpdateAddressData
import com.doubleclick.restaurant.feature.profile.data.visits.VisitsData
import com.doubleclick.restaurant.feature.profile.domain.AddAddressUseCase
import com.doubleclick.restaurant.feature.profile.domain.ChangePasswordUseCase
import com.doubleclick.restaurant.feature.profile.domain.DeleteAddressUseCase
import com.doubleclick.restaurant.feature.profile.domain.ListAddressesUseCase
import com.doubleclick.restaurant.feature.profile.domain.ListOrdersUseCase
import com.doubleclick.restaurant.feature.profile.domain.LogOutUseCase
import com.doubleclick.restaurant.feature.profile.domain.RatingOrderUseCase
import com.doubleclick.restaurant.feature.profile.domain.ShowAddressUseCase
import com.doubleclick.restaurant.feature.profile.domain.ShowOrderUseCase
import com.doubleclick.restaurant.feature.profile.domain.ShowPointsUseCase
import com.doubleclick.restaurant.feature.profile.domain.ShowProfileUseCase
import com.doubleclick.restaurant.feature.profile.domain.ShowVisitsUseCase
import com.doubleclick.restaurant.feature.profile.domain.UpdateAddressUseCase
import com.doubleclick.restaurant.feature.profile.domain.UpdateProfileUseCase
import com.doubleclick.restaurant.feature.shop.cart.CartViewModel
import com.doubleclick.restaurant.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import com.doubleclick.restaurant.feature.shop.cartDetails.domain.CitiesUseCase
import com.doubleclick.restaurant.feature.shop.productDetails.domain.ShowProductUseCase
import com.doubleclick.restaurant.feature.shop.response.Product
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
class ProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val appSettingsSource: AppSettingsSource,
    private val showProfileUseCase: ShowProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val showPointsUseCase: ShowPointsUseCase,
    private val showVisitsUseCase: ShowVisitsUseCase,
    private val listOrdersUseCase: ListOrdersUseCase,
    private val logoutUseCase: LogOutUseCase,
    private val ratingOrderUseCase: RatingOrderUseCase,
    private val showOrderUseCase: ShowOrderUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val listAddressesUseCase: ListAddressesUseCase,
    private val showAddressUseCase: ShowAddressUseCase,
    val showProductsUseCase: ShowProductUseCase,
    private val citiesUseCase: CitiesUseCase
) : BaseViewModel() {

    companion object {
        const val showProfileKey = "SHOWPROFILE"
        const val pointsKey = "POINTS"
        const val visitsKey = "VISITS"
        const val listOrdersKey = "ORDERS"
        const val showOrderKey = "SHOW_ORDER_KEY"
        const val addressesKey = "ADDRESSES_KEY"
        const val citiesKey = "CITIES_KEY"
        const val showProductKey = "SHOW_PRODUCT_KEY"
    }

    private val _showProfile: MutableStateFlow<ProfileDetails?> = MutableStateFlow(savedStateHandle[showProfileKey])
    val showProfile: StateFlow<ProfileDetails?> = _showProfile

    fun showProfile() {
        showProfileUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleShowProfile) }
    }

    private fun handleShowProfile(data: ProfileDetails) {
        with(data) {
            savedStateHandle[showProfileKey] = this
            _showProfile.value = this
        }
    }

    private val _updateProfile: Channel<ProfileDetails> = Channel()
    val updateProfile: Flow<ProfileDetails> = _updateProfile.receiveAsFlow()

    fun updateProfile(
        name: String, phone: String?, password: String, address: String, zip: String?, cardId: String,
        countryId: String, provinceId: String, cityId: String, image: MultipartBody.Part?, email: String
    ) {
        updateProfileUseCase(
            UpdateProfileUseCase.Params(
                initRequestBody(name),
                phone?.let { initRequestBody(it) },
                initRequestBody(password),
                initRequestBody(address),
                zip?.let { initRequestBody(it) },
                initRequestBody(cardId),
                initRequestBody(countryId),
                initRequestBody(provinceId),
                initRequestBody(cityId),
                image,
                initRequestBody(email)
            ), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleUpdateProfile) }
    }

    private fun initRequestBody(data: String) = data.toRequestBody(MultipartBody.FORM)

    private fun handleUpdateProfile(data: ProfileDetails) {
        viewModelScope.launch { _updateProfile.send(data) }
    }


    private val _changePassword: Channel<ProfileDetails> = Channel()
    val changePassword: Flow<ProfileDetails> = _changePassword.receiveAsFlow()

    fun changePassword(
        old_password: String, password: String, password_confirmation: String
    ) {
        changePasswordUseCase(
            ChangePasswordUseCase.Params(ChangePassword(old_password, password, password_confirmation)), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleChangePassword) }
    }

    private fun handleChangePassword(data: ProfileDetails) {
        viewModelScope.launch { _changePassword.send(data) }
    }

    private val _showPoints: MutableStateFlow<List<PointsData>> = MutableStateFlow(savedStateHandle[pointsKey] ?: emptyList())
    val showPoints: StateFlow<List<PointsData>> = _showPoints

    fun showPoints() {
        showPointsUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleShowPoints) }
    }

    private fun handleShowPoints(data: List<PointsData>) {
        with(data) {
            savedStateHandle[pointsKey] = this
            _showPoints.value = this
        }
    }

    private val _showVisits: MutableStateFlow<List<VisitsData>> = MutableStateFlow(savedStateHandle[visitsKey] ?: emptyList())
    val showVisits: StateFlow<List<VisitsData>> = _showVisits

    fun showVisits() {
        showVisitsUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleShowVisits) }
    }

    private fun handleShowVisits(data: List<VisitsData>) {
        with(data) {
            savedStateHandle[visitsKey] = this
            _showVisits.value = this
        }
    }

    fun resetOrdersPage() {
        ordersPage = 1
    }
    private var ordersPage = 1

    private val _listOrders: MutableStateFlow<List<Order>> = MutableStateFlow(savedStateHandle[listOrdersKey] ?: emptyList())
    val listOrders: StateFlow<List<Order>> = _listOrders

    fun listOrders() = listOrdersUseCase(ListOrdersUseCase.Params(ordersPage), viewModelScope, this) {
        it.fold(
            ::handleFailure,
            ::handleListOrdersResponse
        )
    }

    private fun handleListOrdersResponse(data: ListOrdersData) {
        ordersPage++
        ordersQUERYPAGESIZE = data.per_page
        ordersIsLastPage = data.last_page == data.current_page
        ordersOldList.addAll(data.data)
        val newList = mutableListOf<Order>()
        newList.addAll(ordersOldList)
        _listOrders.value = newList
        savedStateHandle[listOrdersKey] = newList
    }

    private val ordersOldList = mutableListOf<Order>()

    var ordersQUERYPAGESIZE = 1
    var ordersIsLastPage = false


    private val _logout: Channel<String> = Channel()
    val logout: Flow<String> = _logout.receiveAsFlow()

    fun doLogout() {
        logoutUseCase(
            UseCase.None(), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleLogout) }
    }

    private fun handleLogout(data: String) {
        viewModelScope.launch { _logout.send(data) }
    }


    private val _rate: Channel<RatingData> = Channel()
    val rate: Flow<RatingData> = _rate.receiveAsFlow()

    fun doRating(product_id: Int, rate: Int, rate_provider: Int) =
        ratingOrderUseCase(RatingOrderUseCase.Params(RatingOrderRequest(product_id, rate, rate_provider)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleRate) }

    private fun handleRate(data: RatingData) {
        viewModelScope.launch { _rate.send(data) }
    }


    private val _showOrder: MutableStateFlow<ShowOrderData?> = MutableStateFlow(savedStateHandle[showOrderKey])
    val showOrder: StateFlow<ShowOrderData?> = _showOrder

    fun getShowOrder(id: String) = showOrderUseCase(
        ShowOrderUseCase.Params(id),
        viewModelScope, this
    ) { it.fold(::handleFailure, ::handleShowOrder) }

    private fun handleShowOrder(data: ShowOrderData) {
        with(data) {
            savedStateHandle[showOrderKey] = this
            _showOrder.value = this
        }
    }

    private val _addAddress: Channel<AddressData> = Channel()
    val addAddress: Flow<AddressData> = _addAddress.receiveAsFlow()

    fun doAddAddress(country_id: String, province_id: String, city_id: String, address: String, zip: String, user_name: String, email: String, phone: String) =
        addAddressUseCase(AddAddressUseCase.Params(AddAddressRequest(country_id, province_id, city_id, address, zip, user_name, email, phone)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleAddAddress) }

    private fun handleAddAddress(data: AddressData) {
        viewModelScope.launch { _addAddress.send(data) }
    }

    private val _showAddress: Channel<AddressData> = Channel()
    val showAddress: Flow<AddressData> = _showAddress.receiveAsFlow()

    fun doShowAddress(id: String) =
        showAddressUseCase(ShowAddressUseCase.Params(id), viewModelScope, this)
        { it.fold(::handleFailure, ::handleShowAddress) }

    private fun handleShowAddress(data: AddressData) {
        viewModelScope.launch { _showAddress.send(data) }
    }

    private val _updateAddress: Channel<UpdateAddressData> = Channel()
    val updateAddress: Flow<UpdateAddressData> = _updateAddress.receiveAsFlow()

    fun doUpdateAddress(addressId: String, countryId: String, provinceId: String, cityId: String, address: String, zip: String, userName: String, email: String, phone: String) {
        updateAddressUseCase(
            UpdateAddressUseCase.Params(addressId, countryId, provinceId, cityId, address, zip, userName, email, phone),
            viewModelScope, this
        ) { it.fold(::handleFailure, ::handleUpdateAddress) }
    }

    private fun handleUpdateAddress(data: UpdateAddressData) {
        viewModelScope.launch { _updateAddress.send(data) }
    }

    private val _deleteAddress: Channel<String> = Channel()
    val deleteAddress: Flow<String> = _deleteAddress.receiveAsFlow()

    fun doDeleteAddress(addressId: String) {
        deleteAddressUseCase(
            DeleteAddressUseCase.Params(addressId),
            viewModelScope, this
        ) { it.fold(::handleFailure, ::handleDeleteAddress) }
    }

    private fun handleDeleteAddress(data: String) {
        viewModelScope.launch { _deleteAddress.send(data) }
    }

    private val _getAddresses: MutableStateFlow<List<AddressesData>?> = MutableStateFlow(savedStateHandle[addressesKey])
    val getAddresses: StateFlow<List<AddressesData>?> = _getAddresses

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


    private val _showProduct: MutableStateFlow<Product?> = MutableStateFlow(savedStateHandle[showProductKey])
    val showProduct: StateFlow<Product?> = _showProduct

    fun getShowProduct(id: String) = showProductsUseCase(
        ShowProductUseCase.Params(id),
        viewModelScope, this
    ) { it.fold(::handleFailure, ::handleShowProducts) }

    private fun handleShowProducts(data: Product) {
        with(data) {
            savedStateHandle[showProductKey] = this
            _showProduct.value = this
        }
    }


    private val _getFilteredCities: MutableStateFlow<List<FilterCitiesData>> = MutableStateFlow(savedStateHandle[CartViewModel.citiesKey] ?: emptyList())
    val getFilteredCities: StateFlow<List<FilterCitiesData>> = _getFilteredCities

    fun getCities(zip: String) {
        citiesUseCase(CitiesUseCase.Params(zip), viewModelScope, this) {
            it.fold(::handleFailure, ::handleGetFilteredCities)
        }
    }

    fun clearCities() {
        savedStateHandle[citiesKey] = null
        _getFilteredCities.value = emptyList()
    }

    private fun handleGetFilteredCities(data: List<FilterCitiesData>) {
        with(data) {
            savedStateHandle[citiesKey] = this
            _getFilteredCities.value = this
        }
    }
}

