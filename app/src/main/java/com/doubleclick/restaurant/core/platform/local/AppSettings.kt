package com.doubleclick.restaurant.core.platform.local

import com.doubleclick.restaurant.core.extension.empty
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val userAccess: UserAccess? = null,
    val cartInventory: CartInventory = CartInventory(),
    val shouldUpdate: Boolean = false,
    val paymentMethod: Int = 0
)

@Serializable
data class UserAccess(
    val address: String? = String.empty(),
    val created_at: String = String.empty(),
    val email: String = String.empty(),
    val fcm_token: String = String.empty(),
    val frist_name: String = String.empty(),
    val id: String = String.empty(),
    val last_name: String = String.empty(),
    val phone: String = String.empty(),
    val token: String = String.empty(),
    val updated_at: String = String.empty()
)

@Serializable
data class CartInventory(
    val size: Int = 0
)