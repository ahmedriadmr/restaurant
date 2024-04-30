package com.doubleclick.restaurant.feature.shop.cartDetails.data.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val cart_item_id: Int,
    val created_at: String,
    val discount: String?,
    val id: Int,
    val presentation: Presentation,
    val presentation_id: Int,
    val price_after_discount: String?,
    val price_per_unit: String,
    val product_id: Int,
    val total: String,
    val units: Int,
    val updated_at: String
) : Parcelable