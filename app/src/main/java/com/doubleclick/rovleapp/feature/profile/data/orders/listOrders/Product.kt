package com.doubleclick.rovleapp.feature.profile.data.orders.listOrders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val items: List<Item>,
    val order_id: Int,
    val product_id: Int,
    val product_name: String
) : Parcelable