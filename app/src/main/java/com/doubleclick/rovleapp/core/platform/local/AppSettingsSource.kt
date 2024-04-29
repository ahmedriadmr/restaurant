package com.doubleclick.rovleapp.core.platform.local

import kotlinx.coroutines.flow.Flow

interface AppSettingsSource {
    fun user(): Flow<UserAccess?>
    fun cartInventory(): Flow<Int>
    suspend fun setCartInventory(inventory: Int)
    suspend fun setGuest()
    suspend fun removeGuest()
    suspend fun updateCart():Boolean
    suspend fun setUpdateCart(updateCart:Boolean)
    fun username(): Flow<String?>
    suspend fun saveUserAccess(userAccess: UserAccess?)

    suspend fun getPaymentMethod(): Int
    suspend fun setPaymentMethod(method: Int)

}
