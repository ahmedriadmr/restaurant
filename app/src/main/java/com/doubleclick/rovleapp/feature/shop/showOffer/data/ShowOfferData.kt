package com.doubleclick.rovleapp.feature.shop.showOffer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowOfferData(
    val activation_amount: String,
    val activation_method: String,
    val created_at: String,
    val description: String,
    val discount: String,
    val discount_type: String,
    val duration: String,
    val end_date: String?,
    val id: Int,
    val level_id: Int,
    val name: String,
    val offer_place: String,
    val products: List<Product>,
    val provider_id: Int,
    val start_date: String,
    val updated_at: String
) : Parcelable