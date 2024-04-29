package com.doubleclick.rovleapp.feature.shop.cartDetails.data.makeOrder.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val created_at: String,
    val discount: Int,
    val id: String,
    val order_id: String,
    val presentation_id: String,
    val price_after_discount: String,
    val price_per_unit: Double,
    val product_id: Int,
    val total: Double,
    val units: String,
    val updated_at: String
) : Parcelable