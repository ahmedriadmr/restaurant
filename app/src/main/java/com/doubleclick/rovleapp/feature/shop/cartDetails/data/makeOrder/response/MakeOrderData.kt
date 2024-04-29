package com.doubleclick.rovleapp.feature.shop.cartDetails.data.makeOrder.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MakeOrderData(
    val address: String,
    val coffee_shop_id: String?,
    val created_at: String,
    val delivery_type: String,
    val device_id: Int,
    val email: String,
    val id: String,
    val items: List<Item>,
    val locker_location: String?,
    val name: String,
    val notes: String?,
    val phone: String,
    val provider_id: String,
    val shipping: Int,
    val status: String,
    val taxes: Int,
    val total: String,
    val updated_at: String,
    val user_id: Int,
    val zip_code: String
) : Parcelable