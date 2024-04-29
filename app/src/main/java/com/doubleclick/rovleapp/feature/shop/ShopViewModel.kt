package com.doubleclick.rovleapp.feature.shop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.rovleapp.core.platform.BaseViewModel
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.feature.shop.response.Product
import com.doubleclick.rovleapp.feature.shop.response.ProductData
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.origins.Origins
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.origins.OriginsData
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.providers.Providers
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.providers.ProvidersData
import com.doubleclick.rovleapp.feature.shop.searchProduct.domain.FilterProductUseCase
import com.doubleclick.rovleapp.feature.shop.searchProduct.domain.GetOriginsUseCase
import com.doubleclick.rovleapp.feature.shop.searchProduct.domain.GetProvidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val allProductsUseCase: AllProductsUseCase,
    val allProductsSortedUseCase: AllProductsSortedUseCase,
    val filterProductUseCase: FilterProductUseCase,
    private val getProvidersUseCase: GetProvidersUseCase,
    private val getOriginsUseCase: GetOriginsUseCase,
    val appSettingsSource: AppSettingsSource
) :
    BaseViewModel() {

    companion object {
        const val allProductKey = "ALL_PRODUCTS_KEY"
        const val allProductSortedKey = "ALL_PRODUCTS_SORTED_KEY"
        const val filterKey = "FILTER_KEY"
        const val providersKey = "PROVIDERS_KEY"
        const val originsKey = "ORIGINS_KEY"
    }

    /** All Product **/
    private val _allProducts: MutableStateFlow<ProductData?> = MutableStateFlow(savedStateHandle[allProductKey])
    val allProducts: StateFlow<ProductData?> = _allProducts
    fun getAllProducts(page: Int = 1, isRefresh: Boolean) {
        if (isRefresh) {
            allProductOldList.clear()
            _allProducts.value = null
        }

        allProductsUseCase(
            AllProductsUseCase.Params(page), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleAllProductsResponse) }
    }

    private fun handleAllProductsResponse(productData: ProductData) {
        allProductOldList.addAll(productData.data)
        val newList = mutableListOf<Product>()
        newList.addAll(allProductOldList)
        _allProducts.value = productData.copy(data = newList)
        savedStateHandle[allProductKey] = newList
    }

    private val allProductOldList = mutableListOf<Product>()


    /** All Product Sorted**/
    private val _allProductsSorted: MutableStateFlow<ProductData?> = MutableStateFlow(savedStateHandle[allProductSortedKey])
    val allProductsSorted: StateFlow<ProductData?> = _allProductsSorted
    private var orderByResult = ""
    private var orderTypeResult = ""

    fun getAllProductsSorted(page: Int = 1, isRefresh: Boolean, orderBy: String = orderByResult, orderType: String = orderTypeResult) {
        if (isRefresh) {
            allProductSortedOldList.clear()
            _allProductsSorted.value = null
        }
        orderByResult = orderBy
        orderTypeResult = orderType
        allProductsSortedUseCase(
            AllProductsSortedUseCase.Params(page, orderBy, orderType), viewModelScope, this
        ) { it.fold(::handleFailure, ::handleAllProductsSortedResponse) }
    }

    private fun handleAllProductsSortedResponse(productData: ProductData) {
        allProductSortedOldList.addAll(productData.data)
        val newList = mutableListOf<Product>()
        newList.addAll(allProductSortedOldList)
        _allProductsSorted.value = productData.copy(data = newList)
        savedStateHandle[allProductSortedKey] = newList
    }

    private val allProductSortedOldList = mutableListOf<Product>()


    /** Search **/
    var page = 1
    private val _filter: MutableStateFlow<ProductData?> = MutableStateFlow(savedStateHandle[filterKey])
    val filter: StateFlow<ProductData?> = _filter

    fun clearFilter() {
        _filter.value = null
    }

    private var queryResult = ""
    private var originsResult: List<Int> = emptyList()
    private var scaScoreFromResult = ""
    private var scaScoreToResult = ""
    private var providersResult: List<String> = emptyList()
    private var altitudeFromResult = ""
    private var altitudeToResult = ""
    private var orderByFilterResult = ""
    private var orderTypeFilterResult = ""
    fun filterProducts(
        query: String = queryResult,
        origins: List<Int> = originsResult,
        scaScoreFrom: String = scaScoreFromResult,
        scaScoreTo: String = scaScoreToResult,
        providers: List<String> = providersResult,
        altitudeFrom: String = altitudeFromResult,
        altitudeTo: String = altitudeToResult,
        orderBy: String = orderByFilterResult,
        orderType: String = orderTypeFilterResult,
        page: Int = 1, isRefresh: Boolean
    ) {
        queryResult = query
        originsResult = origins
        scaScoreFromResult = scaScoreFrom
        scaScoreToResult = scaScoreTo
        providersResult = providers
        altitudeFromResult = altitudeFrom
        altitudeToResult = altitudeTo
        orderByFilterResult = orderBy
        orderTypeFilterResult = orderType

        if (isRefresh) {
            filterOldList.clear()
            _filter.value = null
        }
        filterProductUseCase(
            FilterProductUseCase.Params(query, origins, scaScoreFrom, scaScoreTo, providers, altitudeFrom, altitudeTo, page),
            viewModelScope, this
        ) {
            it.fold(::handleFailure, ::handleSearchProductResponse)
        }
    }

    private fun handleSearchProductResponse(products: ProductData) {
        filterOldList.addAll(products.data)
        val newList = mutableListOf<Product>()
        newList.addAll(filterOldList)
        _filter.value = products.copy(data = newList)
        savedStateHandle[filterKey] = newList
    }

    private val filterOldList = mutableListOf<Product>()


    fun resetProvidersAndOriginsPage() {
        providersPage = 1
        originsPage = 1
    }
    private var providersPage = 1
    private val _getProviders: MutableStateFlow<List<Providers>> = MutableStateFlow(savedStateHandle[providersKey] ?: emptyList())
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
        savedStateHandle[providersKey] = newList
    }

    private val providersOldList = mutableListOf<Providers>()

    var providersQUERYPAGESIZE = 1
    var providersIsLastPage = false



    private var originsPage = 1
    private val _getOrigins: MutableStateFlow<List<Origins>> = MutableStateFlow(savedStateHandle[originsKey] ?: emptyList())
    val getOrigins: StateFlow<List<Origins>> = _getOrigins

    fun getOrigins() {
        getOriginsUseCase(GetOriginsUseCase.Params(originsPage), viewModelScope, this) {
            it.fold(::handleFailure, ::handleOrigins)
        }
    }

    private fun handleOrigins(data: OriginsData) {
        originsPage++
        originsQUERYPAGESIZE = data.per_page
        originsIsLastPage = data.last_page == data.current_page
        originsOldList.clear()
        originsOldList.addAll(data.data)
        val newList = mutableListOf<Origins>()
        newList.addAll(originsOldList)
        _getOrigins.value = newList
        savedStateHandle[originsKey] = newList
    }

    private val originsOldList = mutableListOf<Origins>()

    var originsQUERYPAGESIZE = 1
    var originsIsLastPage = false

}