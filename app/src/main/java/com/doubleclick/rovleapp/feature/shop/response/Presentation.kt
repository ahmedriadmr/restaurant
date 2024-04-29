package com.doubleclick.rovleapp.feature.shop.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Presentation(
    val cart_item_id: Float,
    val created_at: String,
    val discount: String?,
    val id: String,
    var price: Double?,
    val product_id: String?,
    val provider_id: Float,
    val original_price: String,
    var price_after_discount: String?,
    val presentation_id: String,
    val price_per_unit: Double?,
    val total: String,
    var units: Int,
    val updated_at: String,
    val weight: String,
    var isChecked: Boolean = false,
    var isEdited: Boolean = false
) : Parcelable