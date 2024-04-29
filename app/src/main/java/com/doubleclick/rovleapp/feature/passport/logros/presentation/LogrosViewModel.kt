package com.doubleclick.rovleapp.feature.passport.logros.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.core.platform.BaseViewModel
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.feature.passport.logros.data.finishTask.FinishTaskRequest
import com.doubleclick.rovleapp.feature.passport.logros.data.newResponse.NewLogrosData
import com.doubleclick.rovleapp.feature.passport.logros.domain.FinishTaskUseCase
import com.doubleclick.rovleapp.feature.passport.logros.domain.ListLogrosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogrosViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val listLogrosUseCase: ListLogrosUseCase,
    val finishTaskUseCase: FinishTaskUseCase,
    val appSettingsSource: AppSettingsSource
) :
    BaseViewModel() {

    companion object {
        const val newLogrosKey = "LOGROS"

    }


    private val _listLogros: MutableStateFlow<NewLogrosData> = MutableStateFlow(savedStateHandle[newLogrosKey] ?: NewLogrosData.empty)
    val listLogros: StateFlow<NewLogrosData> = _listLogros


    fun listLogros() {
        listLogrosUseCase(
            UseCase.None(), viewModelScope, this) {
            it.fold(::handleFailure, ::handleLogrosResponse)
        }
    }

    private fun handleLogrosResponse(logros: NewLogrosData) {
        with(logros) {
            savedStateHandle[newLogrosKey] = this
            _listLogros.value = this
        }
    }

    private val _finishTask: Channel<String> = Channel()
    val finishTask: Flow<String> = _finishTask.receiveAsFlow()

    fun doTask(taskId: String, activationAmount: String) =
        finishTaskUseCase(FinishTaskUseCase.Params(FinishTaskRequest(taskId, activationAmount)), viewModelScope, this) { it.fold(::handleFailure, ::handleTask) }

    private fun handleTask(data: String) {
        viewModelScope.launch { _finishTask.send(data) }
    }
}