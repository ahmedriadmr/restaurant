package com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDetailsData(
    val cart_items: List<CartItem>,
    val shipping: Double?,
    val taxes: Double?,
    val total_price: Double
) : Parcelable