package com.doubleclick.restaurant.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.home.data.Categories
import com.doubleclick.restaurant.feature.home.domain.CategoriesUseCase
import com.doubleclick.restaurant.feature.home.domain.LogOutUseCase
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
    val appSettingsSource: AppSettingsSource
) :
    BaseViewModel() {

    companion object {
        const val newCategoriesKey = "CATEGORIES"

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

}