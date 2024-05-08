package com.doubleclick.restaurant.feature.home.data.listOrders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val address: String,
    val created_at: String,
    val device_token: String,
    val email: String,
    val email_verified_at: String?,
    val fcm_token: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val otp_code: String,
    val phone: String,
    val status: String,
    val updated_at: String
) : Parcelable