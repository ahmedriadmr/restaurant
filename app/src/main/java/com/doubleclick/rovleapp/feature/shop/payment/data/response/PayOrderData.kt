package com.doubleclick.rovleapp.feature.shop.payment.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayOrderData(
    val address: String,
    val coffee_shop_id: String?,
    val created_at: String,
    val delivery_type: String,
    val device_id: Int,
    val email: String,
    val id: Int,
    val locker_location: String?,
    val name: String,
    val notes: String?,
    val phone: String,
    val product_id: Int,
    val provider: Provider,
    val provider_id: Int,
    val shipping: String?,
    val status: String,
    val taxes: String?,
    val total: String,
    val updated_at: String,
    val user_id: Int,
    val zip_code: String
) : Parcelable