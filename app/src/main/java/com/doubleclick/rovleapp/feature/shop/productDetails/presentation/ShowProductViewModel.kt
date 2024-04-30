package com.doubleclick.restaurant.feature.shop.productDetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.shop.cart.request.putCart.PutCartRequest
import com.doubleclick.restaurant.feature.shop.cart.response.putCart.StoreData
import com.doubleclick.restaurant.feature.shop.cart.usecase.PutCartWithOrWithoutOfferUseCase
import com.doubleclick.restaurant.feature.shop.productDetails.domain.ShowProductUseCase
import com.doubleclick.restaurant.feature.shop.response.Product
import com.doubleclick.restaurant.feature.shop.showOffer.data.ShowOfferData
import com.doubleclick.restaurant.feature.shop.showOffer.domain.ShowOfferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowProductViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val showProductsUseCase: ShowProductUseCase,
    val putCartWithOrWithoutOfferUseCase: PutCartWithOrWithoutOfferUseCase,
    val showOfferUseCase: ShowOfferUseCase,
    val appSettingsSource: AppSettingsSource
) :
    BaseViewModel() {
    companion object {
        const val showProductKey = "SHOW_PRODUCT_KEY"
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


    private val _putCartWithOffer: Channel<StoreData> = Channel()
    val putCartWithOffer: Flow<StoreData> = _putCartWithOffer.receiveAsFlow()


    fun putCartWithOrWithoutOffer(request: PutCartRequest, offerId: String?) {
        putCartWithOrWithoutOfferUseCase(PutCartWithOrWithoutOfferUseCase.Params(request, offerId), viewModelScope, this) {
            it.fold(::handleFailure, ::handlePutCartWithOffer)
        }
    }

    private fun handlePutCartWithOffer(data: StoreData) {
        viewModelScope.launch {
            viewModelScope.launch { appSettingsSource.setUpdateCart(true) }
            _putCartWithOffer.send(data)
        }
    }




    private val _showOffer: Channel<ShowOfferData?> = Channel()
    val showOffer: Flow<ShowOfferData?> = _showOffer.receiveAsFlow()

    fun getShowOffer(id: String) = showOfferUseCase(
        ShowOfferUseCase.Params(id),
        viewModelScope, this
    ) { it.fold(::handleFailure, ::handleShowOffer) }

    private fun handleShowOffer(data: ShowOfferData) {
        viewModelScope.launch { _showOffer.send(data) }
    }
}