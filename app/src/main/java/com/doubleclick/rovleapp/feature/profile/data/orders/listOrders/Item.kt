package com.doubleclick.restaurant.feature.profile.data.orders.listOrders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val created_at: String,
    val discount: Int,
    val id: Int,
    val order_product_id: Int,
    val presentation: Presentation,
    val presentation_id: Int,
    val price_after_discount: Double,
    val price_per_unit: Double,
    val product_id: Int,
    val total: Double,
    val units: String,
    val updated_at: String
) : Parcelable