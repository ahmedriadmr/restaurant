package com.doubleclick.restaurant.core.platform.local

import android.content.Context
import com.doubleclick.restaurant.core.extension.dataStore
import com.doubleclick.restaurant.core.functional.Authenticator.Companion.GUEST
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsSourceImpl @Inject constructor(@ApplicationContext private val context: Context) : AppSettingsSource {
    private val dataStore = context.dataStore
    override fun user(): Flow<UserAccess?> = dataStore.data.map { it.userAccess }
    override fun username(): Flow<String?> = dataStore.data.map { it.userAccess?.frist_name }
    override fun cartInventory(): Flow<Int> = dataStore.data.map { it.cartInventory.size }

    override suspend fun setCartInventory(inventory: Int) {
        dataStore.updateData {
            it.copy(cartInventory = CartInventory(size = inventory))
        }
    }

    override suspend fun setGuest() {
        dataStore.updateData {
            it.copy(userAccess = UserAccess(token = GUEST))
        }
    }

    override suspend fun removeGuest() {
        if (user().firstOrNull()?.token == GUEST) {
            dataStore.updateData {
                it.copy(userAccess = null)
            }
        }
    }

    override suspend fun setUpdateCart(updateCart: Boolean) {
        dataStore.updateData {
            it.copy(shouldUpdate = updateCart)
        }
    }

    override suspend fun updateCart(): Boolean = dataStore.data.map { it.shouldUpdate }.first()

    override suspend fun saveUserAccess(userAccess: UserAccess?) {
        dataStore.updateData {
            it.copy(userAccess = userAccess, cartInventory = if (userAccess == null) CartInventory(0) else CartInventory(cartInventory().first()))
        }
    }

    override suspend fun getPaymentMethod(): Int = dataStore.data.first().paymentMethod

    override suspend fun setPaymentMethod(method: Int) {
        dataStore.updateData {
            it.copy(paymentMethod = method)
        }
    }
}
