package com.doubleclick.rovleapp.feature.shop.payment.data.googlePay.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayOrderGooglePayData(
    val address: String,
    val coffee_shop_id: String?,
    val created_at: String,
    val delivery_type: String,
    val device_id: Int,
    val email: String,
    val id: Int,
    val locker_id: String?,
    val name: String,
    val notes: String?,
    val phone: String,
    val provider_id: Int,
    val shipping: Int,
    val status: String,
    val taxes: Int,
    val total: Int,
    val updated_at: String,
    val user_id: Int,
    val zip_code: String
) : Parcelable