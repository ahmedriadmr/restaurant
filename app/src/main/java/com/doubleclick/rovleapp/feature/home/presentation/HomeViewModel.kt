package com.doubleclick.rovleapp.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.feature.home.data.Categories
import com.doubleclick.rovleapp.feature.home.domain.CategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val getCategoriesUseCase: CategoriesUseCase,
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

}