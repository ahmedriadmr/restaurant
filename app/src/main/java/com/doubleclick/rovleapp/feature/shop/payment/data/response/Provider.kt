package com.doubleclick.rovleapp.feature.shop.payment.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Provider(
    val address: String,
    val city_id: Int,
    val commercial_name: String,
    val country_id: Int,
    val created_at: String,
    val delivery_company: Int,
    val id: Int,
    val manage_stock: Int,
    val mango_id: String?,
    val nif: String,
    val official_name: String,
    val phone: String,
    val province_id: Int,
    val rate: String?,
    val rates_count: Int,
    val updated_at: String,
    val user_id: Int,
    val zip: String
) : Parcelable