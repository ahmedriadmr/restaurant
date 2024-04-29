package com.doubleclick.rovleapp.feature.passport.offers.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.core.platform.BaseViewModel
import com.doubleclick.rovleapp.feature.passport.offers.data.Offers
import com.doubleclick.rovleapp.feature.passport.offers.domain.ListOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val offersUseCase: ListOffersUseCase
) : BaseViewModel() {

    companion object {
        const val offersKey = "OFFERS"
    }

    private val _offers: MutableStateFlow<Offers?> = MutableStateFlow(savedStateHandle[offersKey])
    val offers: StateFlow<Offers?> = _offers

    fun listOffers() = offersUseCase(UseCase.None(), viewModelScope, this) {
        it.fold(
            ::handleFailure,
            ::handleResponse
        )
    }

    private fun handleResponse(data: Offers) {
        with(data) {
            savedStateHandle[offersKey] = this
            _offers.value = this
        }
    }
}