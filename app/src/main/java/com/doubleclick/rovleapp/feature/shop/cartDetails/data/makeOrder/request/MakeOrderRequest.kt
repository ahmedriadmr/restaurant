package com.doubleclick.rovleapp.feature.shop.cartDetails.data.makeOrder.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MakeOrderRequest(
    val provider_id: String,
    val delivery_type: String,
    val zip_code: String,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
    val coffee_shop_id: String,
    val locker_location: String,
    val note: String
) : Parcelable

