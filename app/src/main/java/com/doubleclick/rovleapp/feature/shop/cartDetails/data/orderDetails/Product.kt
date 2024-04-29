package com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val altitude: Int,
    val code: String?,
    val commercial_name: String,
    val description: String?,
    val id: Int,
    val rate: String?,
    val rates_count: Double,
    val sca_score: Double,
    val user_rate: String?
) : Parcelable