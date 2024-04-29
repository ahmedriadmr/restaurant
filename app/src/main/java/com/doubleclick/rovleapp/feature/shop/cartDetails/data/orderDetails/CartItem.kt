package com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val created_at: String,
    val device_id: String?,
    val id: Int,
    val items: List<Item>,
    val notes: String?,
    val passport_offer_id: String?,
    val product: Product,
    val product_id: Int,
    val provider_id: Int,
    val updated_at: String,
    val user_id: Int
) : Parcelable