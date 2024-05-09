package com.doubleclick.restaurant.feature.chef.presentation

import androidx.lifecycle.ViewModel
import com.doubleclick.restaurant.feature.chef.domain.ChefUseCase
import com.doubleclick.restaurant.feature.chef.domain.FinishOrderUserCase
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class ChefViewModel @Inject constructor(
    private val chefUseCase: ChefUseCase,
    private val finishOrderUserCase: FinishOrderUserCase
) : ViewModel() {

    fun getOrders(status: Status) = flow {
        emit(chefUseCase.run(status))
    }.flowOn(Dispatchers.IO)

    fun finishOrder(order_id: String) = flow {
        emit(finishOrderUserCase.run(order_id))
    }.flowOn(Dispatchers.IO)


}