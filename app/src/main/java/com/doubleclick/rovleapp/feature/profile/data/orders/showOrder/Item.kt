package com.doubleclick.restaurant.feature.profile.data.orders.showOrder

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
    val price_after_discount: String?,
    val price_per_unit: Double,
    val product_id: Int,
    val total: Double,
    var units: String,
    val updated_at: String
) : Parcelable

