package com.doubleclick.restaurant.feature.home.data.searchOrders.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemX(
    val category_id: Int,
    val created_at: String,
    val description: String,
    val id: Int,
    val image: String?,
    val name: String,
    val status: String,
    val updated_at: String,
    val vip: String
) : Parcelable