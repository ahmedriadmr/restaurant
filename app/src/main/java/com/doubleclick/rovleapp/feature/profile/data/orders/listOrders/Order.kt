package com.doubleclick.restaurant.feature.profile.data.orders.listOrders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val address: String,
    val coffee_shop_id: String?,
    val created_at: String,
    val delivery_type: String,
    val device_id: Int?,
    val email: String,
    val id: String,
    val locker_location: String?,
    val name: String,
    val notes: String?,
    val phone: String,
    val products: List<Product>,
    val provider: Provider,
    val provider_id: Int,
    val shipping: Int,
    val status: String,
    val taxes: Int,
    val total: String,
    val updated_at: String,
    val user_id: Int,
    val zip_code: String
) : Parcelable