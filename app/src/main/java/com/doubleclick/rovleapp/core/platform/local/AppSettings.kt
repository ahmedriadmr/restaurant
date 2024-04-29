package com.doubleclick.rovleapp.core.platform.local

import com.doubleclick.rovleapp.core.extension.empty
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
    val cardId: String? = String.empty(),
    val cityId: String? = String.empty(),
    val countryId: String? = String.empty(),
    val email: String = String.empty(),
    val hasDashboardAccess: Boolean = false,
    val id: String = String.empty(),
    val image: String? = String.empty(),
    val levelId: String? = String.empty(),
    val name: String = String.empty(),
    val phone: String? = String.empty(),
    val provinceId: String? = String.empty(),
    val token: String? = String.empty(),
    val zip: String? = String.empty()
)

@Serializable
data class CartInventory(
    val size: Int = 0
)