package com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.googlePay.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaySubscriptionGooglePayData(
    val activation_date: String?,
    val address: String?,
    val coffee_shop_id: String?,
    val created_at: String,
    val delivery_type: String,
    val device_id: String?,
    val discount: String?,
    val email: String,
    val end_at: String,
    val id: Int,
    val is_accepted: Int,
    val locker_location: String,
    val name: String,
    val notes: String?,
    val periodicity: Int,
    val phone: String,
    val plan_id: Int,
    val plan_size_id: Int,
    val price: String,
    val shipping: String,
    val start_at: String,
    val status: String,
    val taxes: String?,
    val total: String,
    val updated_at: String,
    val user_id: Int,
    val zip_code: String
) : Parcelable