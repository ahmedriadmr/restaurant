package com.doubleclick.restaurant.feature.shop.cart.response.getCart

import android.os.Parcelable
import com.doubleclick.restaurant.feature.shop.response.Presentation
import com.doubleclick.restaurant.feature.shop.response.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val created_at: String,
    val device_id: Int?,
    val id: String,
    val notes: String?,
    val passport_offer_id: String?,
    var presentations: List<Presentation>,
    val product: Product?,
    val product_id: Int,
    val provider_id: Int,
    val updated_at: String,
    val user_id: Int,
    var isOpened: Boolean = false
) : Parcelable