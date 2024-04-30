package com.doubleclick.restaurant.feature.shop.cart.response.getCart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewCart(
    val cart_items: MutableList<CartItem>,
    val commercial_name: String,
    val id: String,
    val rate: String?,
    val rates_count: Int
) : Parcelable