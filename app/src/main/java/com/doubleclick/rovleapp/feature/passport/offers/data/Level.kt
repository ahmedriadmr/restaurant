package com.doubleclick.rovleapp.feature.passport.offers.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Level(
    val activation_amount: String,
    val activation_method: String,
    val created_at: String,
    val description: String,
    val discount: String,
    val discount_type: String,
    val duration: String,
    val end_date: String?,
    val id: String,
    val level_id: Int,
    val name: String,
    val offer_place: String,
    val provider_id: Int,
    val start_date: String,
    val updated_at: String
) : Parcelable