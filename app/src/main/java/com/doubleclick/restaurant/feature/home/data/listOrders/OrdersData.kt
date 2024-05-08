package com.doubleclick.restaurant.feature.home.data.listOrders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrdersData(
    val created_at: String,
    val id: Int,
    val items: List<Item>,
    val location: String,
    val order_type: String,
    val status: String,
    val table_number: Int,
    val total: Int,
    val updated_at: String,
    val user: User,
    val user_id: Int,
    val waiter_id: Int
) : Parcelable