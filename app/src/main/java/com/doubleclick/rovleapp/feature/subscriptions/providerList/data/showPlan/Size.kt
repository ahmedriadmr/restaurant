package com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    val created_at: String,
    val id: String,
    val plan_id: String,
    val price: String,
    val size: SizeX,
    val size_id: Int,
    val status: String,
    val updated_at: String
) : Parcelable