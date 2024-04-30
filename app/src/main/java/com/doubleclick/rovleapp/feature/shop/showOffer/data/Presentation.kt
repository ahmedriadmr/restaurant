package com.doubleclick.restaurant.feature.shop.showOffer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Presentation(
    val after_dicount_price: Double,
    val created_at: String,
    val id: String,
    val offer_units: Int,
    val price: String,
    val product_id: Int,
    val provider_id: Int,
    val units: Int,
    val updated_at: String,
    val weight: Int
) : Parcelable