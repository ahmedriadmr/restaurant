package com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SizeX(
    val created_at: String,
    val id: String,
    val name: String,
    val provider_id: Int,
    val updated_at: String,
    val weight: String
) : Parcelable