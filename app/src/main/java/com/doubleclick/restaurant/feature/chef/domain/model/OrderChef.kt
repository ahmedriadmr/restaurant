package com.doubleclick.restaurant.feature.chef.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderChef(
    val `data`: List<Data>,
    val status: String
) : Parcelable

@Parcelize
data class Data(
    val id: Int,
    val items: List<Item>,
    val location: String,
    val order_type: String,
    val status: String,
    val table_number: Int?,
    val total: Double,
    val user: User,
    val user_id: Int,
    val created_at: String,
    val waiter_id: Int?
) : Parcelable

@Parcelize
data class Item(
    val id: Int,
    val item_id: Int,
    val number: Int,
    val order_id: Int,
    val size_name: String,
    val size_price: Double,
    val total: Double,
    val item: ItemDish,
    val user_id: Int
) : Parcelable

@Parcelize
data class ItemDish(
    val category_id: Int,
    val created_at: String,
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val updated_at: String,
    val vip: String
) : Parcelable

@Parcelize
data class User(
    val address: String,
    val device_token: String,
    val email: String,
    val fcm_token: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val otp_code: String,
    val phone: String,
    val status: String,
) : Parcelable