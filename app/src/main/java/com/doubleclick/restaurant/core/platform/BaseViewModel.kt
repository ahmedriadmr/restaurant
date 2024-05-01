package com.doubleclick.restaurant.core.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    private val _failure: Channel<Failure> = Channel()
    val failure: Flow<Failure> = _failure.receiveAsFlow()

    protected fun handleFailure(failure: Failure) {
        viewModelScope.launch { _failure.send(failure) }
    }

    private val _loading: Channel<Loading> = Channel()
    val loading: Flow<Loading> = _loading.receiveAsFlow()
    fun handleLoading(loadState: Loading) {
        viewModelScope.launch { _loading.send(loadState) }
    }
}