package com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val altitude: String,
    val code: String,
    val commercial_name: String,
    val created_at: String,
    val description: String,
    val farm: String,
    val grades: String,
    val id: String,
    val origin: String?,
    val owner_name: String?,
    val parent_id: Int?,
    val pivot: PivotX,
    val process: String,
    val provider_id: Int,
    val rate: String?,
    val rates_count: Int,
    val region: String,
    val reviews_rate: String,
    val sca_score: Int,
    val status: String,
    val trade_name: String,
    val updated_at: String
) : Parcelable