package com.doubleclick.restaurant.feature.home.data.searchOrders.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val created_at: String,
    val id: Int,
    val item: ItemX,
    val item_id: Int,
    val number: Int,
    val order_id: Int,
    val size_name: String,
    val size_price: Int,
    val total: Int,
    val updated_at: String,
    val user_id: Int
) : Parcelable